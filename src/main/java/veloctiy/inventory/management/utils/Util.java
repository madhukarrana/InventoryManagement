package veloctiy.inventory.management.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;

public class Util {

    private Logger logger = LoggerFactory.getLogger(Util.class);

    public static String generateUniqueId(){
        return UUID.randomUUID().toString();
    }
}
