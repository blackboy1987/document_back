
package com.igomall.controller.common;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonView;
import com.igomall.service.setting.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.igomall.entity.setting.Area;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller - 地区
 * 
 * @author IGOMALL  Team
 * @version 1.0
 */
@RestController("commonAreaController")
@RequestMapping("/api/common/area")
public class AreaController {

	@Autowired
	private AreaService areaService;

	/**
	 * 地区
	 */
	@GetMapping
	@JsonView(Area.TreeView.class)
	public List<Area> index() {
		return areaService.findRoots();
	}

}