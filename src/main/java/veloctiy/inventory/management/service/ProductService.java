package veloctiy.inventory.management.service;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import veloctiy.inventory.management.database.ProductDao;
import veloctiy.inventory.management.database.SupplierDao;
import veloctiy.inventory.management.entity.Product;
import veloctiy.inventory.management.entity.Supplier;
import veloctiy.inventory.management.exception.BadRequestException;
import veloctiy.inventory.management.exception.InternalServerException;
import veloctiy.inventory.management.model.request.*;
import veloctiy.inventory.management.model.response.*;
import veloctiy.inventory.management.utils.ProductValidator;
import veloctiy.inventory.management.utils.Util;

import java.util.List;

@Component
public class ProductService {

    @Autowired
    private ProductValidator productValidator;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private SupplierDao supplierDao;

    private Logger logger = LoggerFactory.getLogger(ProductService.class);

    public CreateProductResponse create(CreateProductRequest request) throws BadRequestException, InternalServerException {
        logger.info("request received to create product, request - {}", request);
        productValidator.validate(request);
        Supplier supplier = supplierDao.getSupplier(request.getSupplierId());
        if (supplier == null){
            String message = "supplier does not exist for supplier id " + request.getSupplierId();
            logger.error(message);
            throw new BadRequestException(message);
        }
        Product product = getProduct(request);
        productDao.insert(product);
        return CreateProductResponse.createSuccessResponse("product created successfully");
    }

    public UpdateProductResponse update(UpdateProductRequest request) throws BadRequestException, InternalServerException {
        logger.info("request received to update product, request - {}", request);
        productValidator.validate(request);
        Product product = productDao.getProduct(request.getProductId());
        if (product == null){
            String message = "product does not exist for product id " + request.getProductId();
            logger.error(message);
            throw new BadRequestException(message);
        }
        Product updatedProduct = updateProduct(product, request);
        productDao.update(updatedProduct);
        return UpdateProductResponse.createSuccessResponse("product updated successfully");
    }

    public DeleteProductResponse delete(DeleteProductRequest request) throws BadRequestException, InternalServerException {
        logger.info("request received to delete product, request - {}", request);
        productValidator.validate(request);
        Product product = productDao.getProduct(request.getProductId());
        if (product == null){
            String message = "product does not exist for product id " + request.getProductId();
            logger.error(message);
            throw new BadRequestException(message);
        }
        productDao.delete(product);
        return DeleteProductResponse.createSuccessResponse("product updated successfully");
    }

    public ProductDetailsResponse getDetails(String productId) throws BadRequestException, InternalServerException {
        logger.info("request received to get product, product id - {}", productId);
        productValidator.checkBlank("product  id", productId);
        Product product = productDao.getProduct(productId);
        if (product == null){
            String message = "product does not exist for product id " + productId;
            logger.error(message);
            throw new BadRequestException(message);
        }
        ProductDetailsResponse response = getProductDetailsResponse(product);
        return response;
    }

    public List<ProductDetailsResponse> getDetailsByFilter(FilterParams filterParams){

        return null;
    }

    public AdjustStockResponse adjustStock(AdjustStockRequest request) throws BadRequestException, InternalServerException {
        logger.info("request received to adjust stock, request - {}", request);
        productValidator.checkBlank("product id", request.getProductId());
        productValidator.checkNull("adjust quantity", request.getAdjustQuantity());
        Product product = productDao.getProduct(request.getProductId());
        if (product == null){
            String message = "product does not exist for product id " + request.getProductId();
            logger.error(message);
            throw new BadRequestException(message);
        }
        Integer currentQuantity = product.getQuantity();
        Integer updatedQuantity = currentQuantity + request.getAdjustQuantity();
        if (updatedQuantity < 0){
            String message = "quantity " + request.getAdjustQuantity() + " is not available for product " + request.getProductId();
            throw new BadRequestException(message);
        }
        product.setQuantity(updatedQuantity);
        productDao.update(product);
        return AdjustStockResponse.createSuccessResponse("stock is adjusted successfully");
    }

    public Product getProduct(CreateProductRequest request){
        Product product = new Product();
        product.setProductId(Util.generateUniqueId());
        product.setName(request.getName());
        product.setSupplierId(request.getSupplierId());
        product.setPrice(request.getPrice());
        product.setQuantity(request.getQuantity());
        product.setDescription(request.getDescription());
        product.setDeleted(Boolean.FALSE);
        return product;
    }

    public Product updateProduct(Product product, UpdateProductRequest request){
        if (StringUtils.isNotBlank(request.getName())){
            product.setName(request.getName());
        }
        if (StringUtils.isNotBlank(request.getSupplierId())){
            product.setSupplierId(request.getSupplierId());
        }
        if (StringUtils.isNotBlank(request.getDescription())){
            product.setDescription(request.getDescription());
        }
        if (request.getPrice() != null){
            product.setPrice(request.getPrice());
        }
        if (request.getQuantity() != null){
            product.setQuantity(request.getQuantity());
        }
        return product;
    }

    public ProductDetailsResponse getProductDetailsResponse(Product product){
        ProductDetailsResponse productDetailsResponse = new ProductDetailsResponse();
        productDetailsResponse.setProductId(product.getProductId());
        productDetailsResponse.setName(product.getName());
        productDetailsResponse.setDescription(product.getDescription());
        productDetailsResponse.setSupplierId(product.getSupplierId());
        productDetailsResponse.setPrice(product.getPrice());
        productDetailsResponse.setQuantity(product.getQuantity());
        return productDetailsResponse;
    }
}
