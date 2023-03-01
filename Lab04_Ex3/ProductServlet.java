package Servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import Models.Product;

@WebServlet("/ProductServlet")
public class ProductServlet extends HttpServlet {
	private List<Product> products = new ArrayList();
	private Gson gson = new Gson();

	private static final long serialVersionUID = 1L;

	public ProductServlet() {
		super();
	}

	@Override
	public void init() {
		this.products.add(new Product(0, "Samsung Z Clip", 1000));
		this.products.add(new Product(1, "iPhone 14", 2000));
		this.products.add(new Product(2, "Samsung Galaxy Note 9", 600));
		this.products.add(new Product(3, "Nokia 1280", 50));
		this.products.add(new Product(4, "Samsung Galaxy Fold 3", 3000));
		this.products.add(new Product(5, "Asus ROG Phone 4", 1000));
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");

		JsonObject json = new JsonObject();
		JsonArray jsonArray = new JsonArray();
		int jsonCode = 0;
		String jsonMessage = "";

		if (request.getParameter("id") != null) {
			int productId = Integer.parseInt(request.getParameter("id"));
			Product product = null;
			for (Product p: this.products) {
				if (p.getId() == productId) {
					product = p;
					break;
				}
			}

			if (product != null) {
				JsonObject js = new JsonObject();
				js.addProperty("id", product.getId());
				js.addProperty("name", product.getName());
				js.addProperty("price", product.getPrice());
				jsonArray.add(js);

				jsonCode = 0;
				jsonMessage = "Đọc sản phẩm thành công.";
			} else {
				jsonCode = 2;
				jsonMessage = "Không tìm thấy sản phẩm nào với mã số " + productId + ".";
			}
		} else {
			jsonCode = 0;
			jsonMessage = "Đọc sản phẩm thành công.";

			for (Product p: this.products) {
				JsonObject js = new JsonObject();
				js.addProperty("id", p.getId());
				js.addProperty("name", p.getName());
				js.addProperty("price", p.getPrice());
				jsonArray.add(js);
			}
		}

		json.addProperty("code", jsonCode);
		json.addProperty("message", jsonMessage);
		if (jsonArray.size() > 0) {
			json.add("data", jsonArray);
		}

		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		JsonObject json = new JsonObject();
		
		if (!request.getParameter("id").isEmpty() && !request.getParameter("name").isEmpty() && !request.getParameter("price").isEmpty()) {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int price = Integer.parseInt(request.getParameter("price"));
			
			boolean existId = false;
			
			for (Product p: this.products) {
				if (p.getId() == id) {
					json.addProperty("code", 1);
					json.addProperty("message", "Trùng id sản phẩm, vui lòng nhập id khác.");
					existId = true;
					break;
				}
			}
			
			if (!existId) {
				Product product = new Product(id, name, price);
				this.products.add(product);
				json.addProperty("code", 0);
				json.addProperty("message", "Thêm sản phẩm mới thành công.");
			}
		} else {
			json.addProperty("code", 1);
			json.addProperty("message", "Vui lòng nhập đầy đủ id, name, price của sản phẩm.");
		}
		
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
	}

	protected void doPut(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		JsonObject json = new JsonObject();
		
		if (!request.getParameter("id").isEmpty() && !request.getParameter("name").isEmpty() && !request.getParameter("price").isEmpty()) {
			int id = Integer.parseInt(request.getParameter("id"));
			String name = request.getParameter("name");
			int price = Integer.parseInt(request.getParameter("price"));
			
			boolean existId = false;
			
			for (int i = 0; i < this.products.size(); i++) {
				if (this.products.get(i).getId() == id) {
					this.products.get(i).setName(name);
					this.products.get(i).setPrice(price);
					json.addProperty("code", 0);
					json.addProperty("message", "Cập nhật sản phẩm thành công.");
					existId = true;
					break;
				}
			}
			
			if (!existId) {
				json.addProperty("code", 1);
				json.addProperty("message", "Sản phẩm không tồn tại.");
			} 
		} else {
			json.addProperty("code", 1);
			json.addProperty("message", "Vui lòng nhập đầy đủ id, name, price của sản phẩm.");
		}
		
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
	}

	protected void doDelete(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("application/json");
		response.setCharacterEncoding("UTF-8");
		
		JsonObject json = new JsonObject();
		
		if (!request.getParameter("id").isEmpty()) {
			int id = Integer.parseInt(request.getParameter("id"));
			boolean existId = false;
			
			for (int i = 0; i < this.products.size(); i++) {
				if (this.products.get(i).getId() == id) {
					this.products.remove(i);
					existId = true;
					json.addProperty("code", 0);
					json.addProperty("message", "Xóa sản phẩm với id: " + id + " thành công.");
					break;
				}
			}
			
			if (!existId) {
				json.addProperty("code", 1);
				json.addProperty("message", "Sản phẩm không tồn tại.");
			} 
		} else {
			json.addProperty("code", 1);
			json.addProperty("message", "Vui lòng cung cấp id của sản phẩm.");
		}
		
		PrintWriter out = response.getWriter();
		out.print(json.toString());
		out.flush();
	}
}
