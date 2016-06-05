package org.dmc.services.data.repositories.legacy;

import java.sql.ResultSet;
import java.util.ArrayList;

import org.dmc.services.data.entities.legacy.Product;

public class ProductListDao {

	private final String logTag = ProductListDao.class.getName();
	private ResultSet resultSet;
	
	public ArrayList<Product> getProductList(){
		ArrayList<Product> list = new ArrayList<Product>();
		return list;
		}	

}