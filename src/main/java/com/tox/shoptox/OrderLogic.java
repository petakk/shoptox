package com.tox.shoptox;

import org.apache.log4j.Logger;

import javax.swing.text.DateFormatter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class OrderLogic {

    private static Logger logger = Logger.getLogger(OrderLogic.class);

    final public static boolean processOrder(Order order) {

        if (order.getCount() > order.getProduct().getCount()) {
            return false;
        } else {
            logger.info(String.format("Order created for item#%s count#%d, userId=%d, date=%s", order.getProduct().getTitle(), order.getCount(), order.getUser().getId(), new SimpleDateFormat("dd MMM HH:mm", new Locale("eng")).format(new Date())));
            order.getProduct().withdraw(order.getCount());
            OrderDAO.save(order);
            return true;
        }
    }
}
