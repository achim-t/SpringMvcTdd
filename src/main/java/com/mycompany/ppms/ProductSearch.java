package com.mycompany.ppms;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.mycompany.ppms.model.Product;
import com.mycompany.ppms.service.ProductService;

@Controller
public class ProductSearch {

	final Logger logger = LoggerFactory.getLogger(ProductSearch.class);

	@Autowired
	ProductService productService;

	@RequestMapping(value = "/product/search", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseBody
	public Map<String, Object> productSearch(@RequestParam("q") String q)
			throws IOException {
		
		Map<String, Object> jsonMap = new HashMap<String, Object>();
		String keyword = StringUtils.stripToEmpty(q);
		String text = String.format("Could not find any product matches '%s'", keyword);

		logger.info("productSearch called with '" + StringUtils.stripToEmpty(keyword) + "'");
		
		List<Product> matchedProducts = productService.findByNameContains(keyword);

		if(!matchedProducts.isEmpty()){
			jsonMap.put("status", "found");
			jsonMap.put("products", matchedProducts);
		}
		else {
			jsonMap.put("status", "not found");
			jsonMap.put("text", text);
		}
		
		return jsonMap;
	}
	
	@RequestMapping(value = "/product/searchForm")
	public String productSearchForm() {
		return "product/searchForm";
	}
	
}
