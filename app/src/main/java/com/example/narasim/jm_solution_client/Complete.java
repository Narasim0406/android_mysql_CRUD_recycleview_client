package com.example.narasim.jm_solution_client;

/**
 * Created by Belal on 10/18/2017.
 */

public class Complete {
    private int id;
    private String title;
//    private String shortdesc;
//    private double rating;
//    private double price;
//    private String image;
    //  private int bid;
//    private int pid;
  //  private String product;
    private String model;
    private String quantity;
    private String del_date;
    private String con_name;
    private String mobile;
    private String product;
    private String username;
  //  private String service;
//    private String book_date;
    //  private String feed;
//    private String hint;
    //  private String subject;
    private String status;

    public Complete(int id,
                    String title,
//                    String shortdesc,
//                    double rating,
//                    double price,
//                    String image,
                    //             int bid,
                    //           int pid,
//                    String product,
                    String model,
                    String quantity,
                    String del_date,
                    String con_name,
                    String mobile,
                    String product,
                    String username,
         //           String status)
                   //String service,
                  // String book_date)
    //             String feed,
    //               String hint)
    //    String subject,
      String status)
    {
        this.id = id;
        this.title = title;
//        this.shortdesc = shortdesc;
//        this.rating = rating;
//        this.price = price;
//        this.image = image;
        //this.bid = bid;
        //  this.pid = pid;
  //      this.product = product;
        this.model = model;
        this.quantity = quantity;
        this.del_date = del_date;
        this.con_name = con_name;
        this.mobile = mobile;
        this.product = product;
        this.username = username;
//        this.service = service;
  //      this.book_date = book_date;
        //      this.feed = feed;
//        this.hint = hint;
        //    this.subject = subject;
        this.status = status;
    }

    public int getId() { return id;}

    public String getTitle() { return title;}

//    public String getShortdesc() { return shortdesc;}

//    public double getRating() { return rating;}

//    public double getPrice() { return price;}

    //public String getSubjet() { return subject;}

//    public String getImage() { return image;}

//    public int getBid() { return bid;}

    // public int getPid() { return pid;}

    //public String getProduct() { return product;}

    public String getModel() {return model;}

    public String getQuantity() { return quantity;}

    public String getDel_date() { return del_date;}

    public String getCon_name() { return con_name;}

    public String getMobile() { return mobile;}

    public String getProduct() { return product;}

    public String getusername() {return username;}

//    public String getService() { return service;}

//    public String getBook_date(){ return book_date;}

//    public String getFeed() {return feed;}

//    public String getHint() { return hint;}

    public String getStatus() { return status;}

}
