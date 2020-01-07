
package com.igomall.dao.impl;

import com.igomall.dao.SnDao;
import com.igomall.entity.Sn;
import com.igomall.util.FreeMarkerUtils;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

import javax.persistence.EntityManager;
import javax.persistence.LockModeType;
import javax.persistence.PersistenceContext;
import java.io.IOException;

/**
 * Dao - 序列号
 * 
 * @author blackboy
 * @version 1.0
 */
@Repository
public class SnDaoImpl extends BaseDaoImpl<Sn,Long> implements SnDao, InitializingBean {

	/**
	 * 订单编号生成器
	 */
	private HiloOptimizer courseHiloOptimizer;
	private HiloOptimizer partHiloOptimizer;
	private HiloOptimizer chapterHiloOptimizer;
	private HiloOptimizer lessonHiloOptimizer;
	private HiloOptimizer answerHiloOptimizer;


	@PersistenceContext
	private EntityManager entityManager;

	@Value("${sn.course.prefix}")
	private String coursePrefix;
	@Value("${sn.course.maxLo}")
	private int courseMaxLo;


	@Value("${sn.part.prefix}")
	private String partPrefix;
	@Value("${sn.part.maxLo}")
	private int partMaxLo;


	@Value("${sn.chapter.prefix}")
	private String chapterPrefix;
	@Value("${sn.chapter.maxLo}")
	private int chapterMaxLo;


	@Value("${sn.lesson.prefix}")
	private String lessonPrefix;
	@Value("${sn.lesson.maxLo}")
	private int lessonMaxLo;

	@Value("${sn.answer.prefix}")
	private String answerPrefix;
	@Value("${sn.answer.maxLo}")
	private int answerMaxLo;

	/**
	 * 初始化
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		courseHiloOptimizer = new HiloOptimizer(Sn.Type.course, coursePrefix, courseMaxLo);
		partHiloOptimizer = new HiloOptimizer(Sn.Type.part, partPrefix, partMaxLo);
		chapterHiloOptimizer = new HiloOptimizer(Sn.Type.chapter, chapterPrefix, chapterMaxLo);
		lessonHiloOptimizer = new HiloOptimizer(Sn.Type.lesson, lessonPrefix, lessonMaxLo);;
		answerHiloOptimizer = new HiloOptimizer(Sn.Type.answer, answerPrefix, answerMaxLo);
	}

	/**
	 * 生成序列号
	 * 
	 * @param type
	 *            类型
	 * @return 序列号
	 */
	@Override
	public String generate(Sn.Type type) {
		Assert.notNull(type,"");

		switch (type) {
			case course:
				return courseHiloOptimizer.generate();
			case part:
				return partHiloOptimizer.generate();
			case chapter:
				return chapterHiloOptimizer.generate();
			case lesson:
				return lessonHiloOptimizer.generate();
			case answer:
				return answerHiloOptimizer.generate();
			default:
				return null;
		}
	}

	/**
	 * 获取末值
	 * 
	 * @param type
	 *            类型
	 * @return 末值
	 */
	private long getLastValue(Sn.Type type) {
		String jpql = "select sn from Sn sn where sn.type = :type";
		long lastValue = 1;
		try {
			Sn sn = entityManager.createQuery(jpql, Sn.class).setLockMode(LockModeType.PESSIMISTIC_WRITE).setParameter("type", type).getSingleResult();
			lastValue = sn.getLastValue();
			sn.setLastValue(lastValue + 1);
			return lastValue;
		}catch (Exception e){
			e.printStackTrace();
			Sn sn = new Sn();
			sn.setLastValue(lastValue+1);
			sn.setType(type);
			super.persist(sn);
			return lastValue;
		}
	}

	/**
	 * 高低位算法生成器
	 */
	private class HiloOptimizer {

		/**
		 * 类型
		 */
		private Sn.Type type;

		/**
		 * 前缀
		 */
		private String prefix;

		/**
		 * 最大低位值
		 */
		private int maxLo;

		/**
		 * 低位值
		 */
		private int lo;

		/**
		 * 高位值
		 */
		private long hi;

		/**
		 * 末值
		 */
		private long lastValue;

		/**
		 * 构造方法
		 * 
		 * @param type
		 *            类型
		 * @param prefix
		 *            前缀
		 * @param maxLo
		 *            最大低位值
		 */
		HiloOptimizer(Sn.Type type, String prefix, int maxLo) {
			this.type = type;
			this.prefix = prefix != null ? prefix.replace("{", "${") : "";
			this.maxLo = maxLo;
			this.lo = maxLo + 1;
		}

		/**
		 * 生成序列号
		 * 
		 * @return 序列号
		 */
		public synchronized String generate() {
			if (lo > maxLo) {
				lastValue = getLastValue(type);
				lo = lastValue == 0 ? 1 : 0;
				hi = lastValue * (maxLo + 1);
			}
			try {
				return FreeMarkerUtils.process(prefix) + (hi + lo++);
			} catch (IOException e) {
				throw new RuntimeException(e.getMessage(), e);
			} catch (TemplateException e) {
				throw new RuntimeException(e.getMessage(), e);
			}
		}
	}

}