package com.ecommerce.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.API.ApiController;
import com.ecommerce.entity.UseEntity;
import com.ecommerce.service.UseInterface;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
@Api(value = "USE CONTROLLER")
public class UseController extends ApiController {

	@Autowired
	private UseInterface productService;

	@CrossOrigin
	@PostMapping(value = POST_USE)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<UseEntity> createProduct(@RequestBody UseEntity product,
			@RequestHeader("Authorization") String authorizationHeader) {
		String jwtToken = extractBearerToken(authorizationHeader);
		UseEntity createdProduct = productService.createProduct(product);
		return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
	}

	@CrossOrigin
	@DeleteMapping(value = DELETE_USER)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<Void> deleteProduct(@PathVariable Long productId,
			@RequestHeader("Authorization") String authorizationHeader) {
		String jwtToken = extractBearerToken(authorizationHeader);

		productService.deleteProduct(productId);
		return ResponseEntity.noContent().build();
	}

	@CrossOrigin
	@GetMapping(value = GET_ALL_USER)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<List<UseEntity>> getAllProducts(@RequestHeader("Authorization") String authorizationHeader) {
		String jwtToken = extractBearerToken(authorizationHeader);
		List<UseEntity> products = productService.getAllProducts();
		return new ResponseEntity<>(products, HttpStatus.OK);
	}

	@CrossOrigin
	@GetMapping(value = USE_BY_ID)
	@ApiImplicitParams({
			@ApiImplicitParam(name = "Authorization", value = "Bearer token", required = true, dataType = "string", paramType = "header") })
	public ResponseEntity<UseEntity> getProductById(@PathVariable Long productId,
			@RequestHeader("Authorization") String authorizationHeader) {
		String jwtToken = extractBearerToken(authorizationHeader);
		UseEntity product = productService.getProductById(productId);
		return new ResponseEntity<>(product, HttpStatus.OK);
	}

	private String extractBearerToken(String authorizationHeader) {
		if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
			return authorizationHeader.substring(7);
		}
		return null;
	}
}
