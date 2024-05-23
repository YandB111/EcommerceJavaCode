package com.ecommerce.service;

import java.util.List;

import com.ecommerce.entity.UseEntity;

public interface UseInterface {
	UseEntity createProduct(UseEntity product);

	void deleteProduct(Long productId);

	List<UseEntity> getAllProducts();

	UseEntity getProductById(Long productId);
}
