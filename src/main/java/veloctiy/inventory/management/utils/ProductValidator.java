package veloctiy.inventory.management.utils;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import veloctiy.inventory.management.exception.BadRequestException;
import veloctiy.inventory.management.model.request.CreateProductRequest;
import veloctiy.inventory.management.model.request.DeleteProductRequest;
import veloctiy.inventory.management.model.request.UpdateProductRequest;

@Component
public class ProductValidator {

    private Logger logger = LoggerFactory.getLogger(ProductValidator.class);

    public void validate(CreateProductRequest request) throws BadRequestException {
        checkNull("create product request", request);
        checkBlank("product name", request.getName());
        checkBlank("supplier id", request.getSupplierId());
        checkNull("product price", request.getPrice());
        checkNull("product quantity", request.getQuantity());
        checkPositiveValue("product price", request.getPrice());
        checkPositiveValue("product quantity", request.getQuantity());
    }

    public void validate(UpdateProductRequest request) throws BadRequestException {
        checkNull("update product request", request);
        checkBlank("product id", request.getProductId());
        if (request.getPrice() != null){
            checkPositiveValue("product price", request.getPrice());
        }
        if (request.getQuantity() != null){
            checkPositiveValue("product quantity", request.getQuantity());
        }
    }

    public void validate(DeleteProductRequest request) throws BadRequestException {
        checkNull("delete product request", request);
        checkBlank("product id", request.getProductId());
    }

    public void checkNull(String fieldName, Object value) throws BadRequestException {
        if (value == null){
            String message = fieldName + " is null";
            logger.error(message);
            throw new BadRequestException(message);
        }
    }

    public void checkBlank(String fieldName, String value) throws BadRequestException {
        if (StringUtils.isBlank(value)){
            String message = fieldName + " is blank";
            logger.error(message);
            throw new BadRequestException(message);
        }
    }

    public void checkPositiveValue(String fieldName, Integer value) throws BadRequestException {
        if (value <= 0){
            String message = fieldName + " value is not correct";
            logger.error(message);
            throw new BadRequestException(message);
        }
    }

    public void checkPositiveValue(String fieldName, Double value) throws BadRequestException {
        if (value <= 0d){
            String message = fieldName + " value is not correct";
            logger.error(message);
            throw new BadRequestException(message);
        }
    }
}
