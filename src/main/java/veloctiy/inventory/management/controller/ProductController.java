package veloctiy.inventory.management.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import veloctiy.inventory.management.exception.BadRequestException;
import veloctiy.inventory.management.exception.InternalServerException;
import veloctiy.inventory.management.model.request.*;
import veloctiy.inventory.management.model.response.*;
import veloctiy.inventory.management.service.ProductService;

import java.util.Arrays;

@RestController
@RequestMapping(value = "/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    private Logger logger = LoggerFactory.getLogger(ProductController.class);

    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> create(@RequestBody CreateProductRequest request){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.
                    create(request));
        } catch (BadRequestException exception){
            logger.error("error occurred while creating product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(CreateProductResponse.createFailureResponse(exception.getMessage()));
        } catch (InternalServerException exception){
            logger.error("error occurred while creating product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CreateProductResponse.createFailureResponse(exception.getMessage()));
        } catch (Exception exception){
            logger.error("error occurred while creating product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(CreateProductResponse.createFailureResponse(exception.getMessage()));
        }
    }

    @RequestMapping(value = "/update", method = RequestMethod.PUT, produces = "application/json")
    public ResponseEntity<Object> update(@RequestBody UpdateProductRequest request){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.update(request));
        } catch (BadRequestException exception){
            logger.error("error occurred while updating product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(UpdateProductResponse.createFailureResponse(exception.getMessage()));
        } catch (Exception exception){
            logger.error("error occurred while updating product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(UpdateProductResponse.createFailureResponse(exception.getMessage()));
        }
    }

    @RequestMapping(value = "/delete", method = RequestMethod.DELETE, produces = "application/json")
    public ResponseEntity<Object> delete(@RequestBody DeleteProductRequest request){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.delete(request));
        } catch (BadRequestException exception){
            logger.error("error occurred while deleting product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(DeleteProductResponse.createFailureResponse(exception.getMessage()));
        } catch (Exception exception){
            logger.error("error occurred while deleting product. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(DeleteProductResponse.createFailureResponse(exception.getMessage()));
        }
    }

    @RequestMapping(value = "/details", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getDetails(@RequestParam(value = "product_id") String productId){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getDetails(productId));
        } catch (BadRequestException exception){
            logger.error("error fetching product details. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(GenericResponse.createFailureResponse(exception.getMessage()));
        } catch (Exception exception){
            logger.error("error fetching product details. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(GenericResponse.createFailureResponse(exception.getMessage()));
        }
    }

    @RequestMapping(value = "/details/filter", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity<Object> getDetailsByFilter(@RequestBody FilterParams filterParams){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.getDetailsByFilter(filterParams));
        }catch (Exception exception){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(exception.getMessage());
        }
    }

    @RequestMapping(value = "/stock/adjust", method = RequestMethod.POST, produces = "application/json")
    public ResponseEntity<Object> getDetails(AdjustStockRequest request){
        try {
            return ResponseEntity.status(HttpStatus.OK).body(productService.adjustStock(request));
        } catch (BadRequestException exception){
            logger.error("error while adjusting product stock. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(AdjustStockResponse.createFailureResponse(exception.getMessage()));
        } catch (Exception exception){
            logger.error("error while adjusting product stock. message - {}, stack trace - {}",
                    exception.getMessage(), Arrays.asList(exception.getStackTrace()));
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(AdjustStockResponse.createFailureResponse(exception.getMessage()));
        }
    }
}
