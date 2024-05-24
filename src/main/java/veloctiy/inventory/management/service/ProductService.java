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

import java.util.ArrayList;
import java.util.HashMap;
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

    public GenericResponse create(CreateProductRequest request) throws BadRequestException, InternalServerException {
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
        return GenericResponse.createSuccessResponse("product created successfully");
    }

    public GenericResponse update(UpdateProductRequest request) throws BadRequestException, InternalServerException {
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
        return GenericResponse.createSuccessResponse("product updated successfully");
    }

    public GenericResponse delete(DeleteProductRequest request) throws BadRequestException, InternalServerException {
        logger.info("request received to delete product, request - {}", request);
        productValidator.validate(request);
        Product product = productDao.getProduct(request.getProductId());
        if (product == null){
            String message = "product does not exist for product id " + request.getProductId();
            logger.error(message);
            throw new BadRequestException(message);
        }
        productDao.delete(product);
        return GenericResponse.createSuccessResponse("product updated successfully");
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
        return getProductDetailsResponse(product);
    }

    public List<ProductDetailsResponse> getDetailsByFilter(FilterParams filterParams) throws BadRequestException, InternalServerException {
        logger.info("request received to get product list, filter - {}", filterParams);
        productValidator.checkNull("filter params",filterParams);
        HashMap<String, Object> filter = getFiter(filterParams);
        List<Product> productList = productDao.getProduct(filter);
        if (productList == null){
            String message = "product does not exist for given filter " + filterParams;
            logger.error(message);
            throw new BadRequestException(message);
        }
        List<ProductDetailsResponse> productDetailsResponseList = new ArrayList<>();
        for (Product product : productList){
            ProductDetailsResponse productDetailsResponse = getProductDetailsResponse(product);
            productDetailsResponseList.add(productDetailsResponse);
        }
        return productDetailsResponseList;
    }

    public GenericResponse adjustStock(AdjustStockRequest request) throws BadRequestException, InternalServerException {
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
        return GenericResponse.createSuccessResponse("stock is adjusted successfully");
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

    public HashMap<String, Object> getFiter(FilterParams filterParams){
        HashMap<String, Object> filter = new HashMap<>();
        if (filterParams.getSupplierId() != null){
            filter.put(Product.supplierIdColumnMapping, filterParams.getSupplierId());
        }
        if (filterParams.getPriceRange() != null){
            FilterParams.PriceRange priceRange = filterParams.getPriceRange();
            Double start = priceRange.getStart();
            Double end = priceRange.getEnd();
            filter.put(Product.startPriceValue, start);
            filter.put(Product.endPriceValue, end);
        }
        return filter;
    }
}
