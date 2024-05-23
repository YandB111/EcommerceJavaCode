package com.ecommerce.serviceImpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.Repo.UseRepo;
import com.ecommerce.entity.UseEntity;
import com.ecommerce.service.UseInterface;

@Service
public class UseServiceImpl implements UseInterface {

	@Autowired
	private UseRepo productRepository;

	@Override
	public UseEntity createProduct(UseEntity product) {
		return productRepository.save(product);
	}

	@Override
	public void deleteProduct(Long productId) {
		productRepository.deleteById(productId);
	}

	@Override
	public List<UseEntity> getAllProducts() {
		return productRepository.findAll();
	}

	@Override
	public UseEntity getProductById(Long productId) {
		return productRepository.findById(productId).orElse(null);
	}

}
