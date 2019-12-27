package com.tox.shoptox;
public class Product {

    private final static String[] images = new String[]{"https://lh3.googleusercontent.com/ui6lTW9dM-4tOuU4pf4_SlBg7zf6duO1y_xD8fsCIRLvjxWAvrg6OsXQuHQtqvb7ZHSVXjaVlPxvZW2_Cec=w160-h230-rw", "https://lh3.googleusercontent.com/FpgUYxmdF_SS-DG2K2AjOL1_-RTUZEXjwn_XcM5CTX51JQ1Z1b9UUN-h8Io00XmqGyfc6MZd5bpt26VIGw=w160-h230-rw", "https://lh3.googleusercontent.com/bXKOHo27Zte0QUFVVqjx3hpgg4UzruBuws6Rcg0bmnZMt0X0RT6T4FaqyIHgQHPnoLQKPsjTbx7__JAXrSVI=w160-h230-rw", "https://lh3.googleusercontent.com/8yPSHq19kEZG3qLnGHM2QSXC84zTV3NtHBrKNQ4X45DdAeZpLTBBhbodzH9n8FBqlRDQ25P4Cj2veIULDMM=w160-h230-rw", "https://lh3.googleusercontent.com/teFHqcnfd1dfJztpbapQvfJE9I6qPuYlU5ezeoFAbmpKSFsSjumDCyTrDA53jwO9B5-JVusIqif69JQ6gMiQ=w160-h230-rw"};

    private long id;

    private String title;
    private String description;
    private String image;

    private double rating;
    private double cost;
    private int count;


    public Product() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        int i = (int)(id % 5);
        return images[i];
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }


    public void withdraw(int count) {
        if (count > getCount()) {
            throw new IllegalArgumentException();
        }
        setCount(getCount() - count);
    }
}
