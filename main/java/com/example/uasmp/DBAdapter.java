package com.example.uasmp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBAdapter extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "zenbukaemasuDB.db";

    private static final String TABLE_USERS = "users";
    private static final String COLUMN_UID = "_uid";
    private static final String COLUMN_UUSERNAME = "_uusername";
    private static final String COLUMN_UPASSWORD = "_upassword";
    private static final String COLUMN_UNAME = "_uname";
    private static final String COLUMN_UEMAIL = "_uemail";
    private static final String COLUMN_UPHONE = "_uphone";
    private static final String COLUMN_UADDRESS = "_uaddress";
    private static final String COLUMN_UBDATE = "_ubdate";

    private static final String TABLE_PRODUCTS = "products";
    private static final String COLUMN_PID = "_pid";
    private static final String COLUMN_PNAME = "_pname";
    private static final String COLUMN_PQTY = "_pqty";
    private static final String COLUMN_PPRICE = "_pprice";
    private static final String COLUMN_POID = "_poid";

    private static final String TABLE_OUTLETS = "outlets";
    private static final String COLUMN_OID = "_oid";
    private static final String COLUMN_ONAME = "_oname";
    private static final String COLUMN_OLATITUDE = "_olatitude";
    private static final String COLUMN_OLONGITUDE = "_olongitude";

    private static final String TABLE_TRANSACTIONS = "transactions";
    private static final String COLUMN_TID = "_tid";
    private static final String COLUMN_TQTY = "_tqty";
    private static final String COLUMN_TPAYMENT = "_tpayment";
    private static final String COLUMN_TUID = "_tuid";
    private static final String COLUMN_TPID = "_tpid";

    SQLiteDatabase db;

    public DBAdapter(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        db = this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db){
        String[] query = new String[4];

        query[0] = "CREATE TABLE " + TABLE_USERS + " (" +
                COLUMN_UID + " TEXT PRIMARY KEY, " +
                COLUMN_UUSERNAME + " TEXT NOT NULL, " +
                COLUMN_UPASSWORD + " TEXT NOT NULL, " +
                COLUMN_UNAME + " TEXT NOT NULL, " +
                COLUMN_UEMAIL + " TEXT NOT NULL, " +
                COLUMN_UPHONE + " TEXT NOT NULL, " +
                COLUMN_UADDRESS + " TEXT NOT NULL, " +
                COLUMN_UBDATE + " DATE NOT NULL)";

        query[1] = "CREATE TABLE " + TABLE_PRODUCTS + " (" +
                COLUMN_PID + " TEXT PRIMARY KEY, " +
                COLUMN_PNAME + " TEXT NOT NULL, " +
                COLUMN_PQTY + " INTEGER NOT NULL, " +
                COLUMN_PPRICE + " LONG NOT NULL, " +
                COLUMN_POID + " TEXT NOT NULL)";

        query[2] = "CREATE TABLE " + TABLE_OUTLETS + " (" +
                COLUMN_OID + " TEXT PRIMARY KEY, " +
                COLUMN_ONAME + " TEXT NOT NULL, " +
                COLUMN_OLATITUDE + " DOUBLE NOT NULL, " +
                COLUMN_OLONGITUDE + " DOUBLE NOT NULL)";

        query[3] = "CREATE TABLE " + TABLE_TRANSACTIONS + " (" +
                COLUMN_TID + " TEXT PRIMARY KEY, " +
                COLUMN_TQTY + " INTEGER NOT NULL, " +
                COLUMN_TPAYMENT + " TEXT NOT NULL, " +
                COLUMN_TUID + " TEXT NOT NULL, " +
                COLUMN_TPID + " TEXT NOT NULL)";

        for(int i = 0; i < query.length; i++){
            db.execSQL(query[i]);
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion){
        String[] query = new String[4];

        query[0] = "DROP TABLE IF EXISTS " + TABLE_USERS;
        query[1] = "DROP TABLE IF EXISTS " + TABLE_PRODUCTS;
        query[2] = "DROP TABLE IF EXISTS " + TABLE_OUTLETS;
        query[3] = "DROP TABLE IF EXISTS" + TABLE_TRANSACTIONS;

        for(int i = 0; i < query.length; i++){
            db.execSQL(query[i]);
        }

        onCreate(db);
    }

    @Override
    public synchronized void close() {
        super.close();
    }

    public void insertUser(User u){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_UID, u.getId());
        cv.put(COLUMN_UUSERNAME, u.getUsername());
        cv.put(COLUMN_UPASSWORD, u.getPassword());
        cv.put(COLUMN_UNAME, u.getName());
        cv.put(COLUMN_UEMAIL, u.getEmail());
        cv.put(COLUMN_UPHONE, u.getPhone());
        cv.put(COLUMN_UADDRESS, u.getAddress());
        cv.put(COLUMN_UBDATE, u.getBdate());

        db.insert(TABLE_USERS, null, cv);
    }

    public void insertProduct(Product p){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PID, p.getId());
        cv.put(COLUMN_PNAME, p.getName());
        cv.put(COLUMN_PQTY, p.getQty());
        cv.put(COLUMN_PPRICE, p.getPrice());
        cv.put(COLUMN_POID, p.getShop().getId());

        db.insert(TABLE_PRODUCTS, null, cv);
    }

    public void insertOutlet(Outlet o){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_OID, o.getId());
        cv.put(COLUMN_ONAME, o.getName());
        cv.put(COLUMN_OLATITUDE, o.getLatitude());
        cv.put(COLUMN_OLONGITUDE, o.getLongitude());

        db.insert(TABLE_OUTLETS, null, cv);
    }

    public void insertTransaction(Transaction t){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_TID, t.getId());
        cv.put(COLUMN_TQTY, t.getQty());
        cv.put(COLUMN_TPAYMENT, t.getPayment());
        cv.put(COLUMN_TUID, t.getBuyer().getId());
        cv.put(COLUMN_TPID, t.getItem().getId());

        db.insert(TABLE_TRANSACTIONS, null, cv);
    }

    public User getUserById(String id){
        User user = null;
        for(User u : getAllUsers()){
            if(u.getId().equals(id)){
                user = u;
                break;
            }
        }

        return user;
    }

    public ArrayList<User> getAllUsers(){
        String query = "SELECT * FROM " + TABLE_USERS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<User> allUsers = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                allUsers.add(new User(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(6),
                        cursor.getString(7)
                ));
            }
            while(cursor.moveToNext());

        }
        cursor.close();

        return allUsers;
    }

    public Product getProductById(String id){
        Product product = null;
        for(Product p : getAllProducts()){
            if(p.getId().equals(id)){
                product = p;
                break;
            }
        }

        return product;
    }

    public ArrayList<Product> getAllProducts(){
        String query = "SELECT * FROM " + TABLE_PRODUCTS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Product> allProducts = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                allProducts.add(new Product(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getInt(2),
                        cursor.getLong(3),
                        getOutletById(cursor.getString(4))
                ));
            }
            while(cursor.moveToNext());

        }
        cursor.close();

        return allProducts;
    }

    public Outlet getOutletById(String id){
        Outlet outlet = null;
        for(Outlet o : getAllOutlets()){
            if(o.getId().equals(id)){
                outlet = o;
                break;
            }
        }

        return outlet;
    }

    public ArrayList<Outlet> getAllOutlets(){
        String query = "SELECT * FROM " + TABLE_OUTLETS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Outlet> allOutlets = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                allOutlets.add(new Outlet(
                        cursor.getString(0),
                        cursor.getString(1),
                        cursor.getDouble(2),
                        cursor.getDouble(3)
                ));
            }
            while(cursor.moveToNext());

        }
        cursor.close();

        return allOutlets;
    }

    public Transaction getTransactionById(String id){
        Transaction transaction = null;
        for(Transaction t : getAllTransactions()){
            if(t.getId().equals(id)){
                transaction = t;
                break;
            }
        }

        return transaction;
    }

    public ArrayList<Transaction> getAllTransactions(){
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<Transaction> allTransactions = new ArrayList<>();

        if(cursor.moveToFirst()){
            do{
                allTransactions.add(new Transaction(
                        cursor.getString(0),
                        cursor.getInt(1),
                        cursor.getString(2),
                        getUserById(cursor.getString(3)),
                        getProductById(cursor.getString(4))
                ));
            }
            while(cursor.moveToNext());
        }

        return allTransactions;
    }

    public boolean updateProduct(Product newProduct){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PNAME, newProduct.getName());
        cv.put(COLUMN_PQTY, newProduct.getQty());
        cv.put(COLUMN_PPRICE, newProduct.getPrice());
        cv.put(COLUMN_POID, newProduct.getShop().getId());

        return db.update(TABLE_PRODUCTS, cv, COLUMN_PID + " = \"" + newProduct.getId() + "\"", null) > 0;
    }

    public boolean deleteProductByName(String name) {
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_PNAME + " = \"" + name + "\"";
        Cursor cursor = db.rawQuery(query, null);
        boolean result = false;

        if (cursor.moveToFirst()) {
            String productID = cursor.getString(0);
            db.delete(TABLE_PRODUCTS, COLUMN_PID + " = ?", new String[]{productID});
            result = true;
        }
        cursor.close();

        return result;
    }

    public boolean deleteTransactionByProductId(String pId) {
        String query = "SELECT * FROM " + TABLE_TRANSACTIONS + " WHERE " + COLUMN_TPID + " = \"" + pId + "\"";
        Cursor cursor = db.rawQuery(query, null);
        boolean result = false;

        if(cursor.moveToFirst()) {
            String transactionID = cursor.getString(0);
            db.delete(TABLE_TRANSACTIONS, COLUMN_TID + " = ?", new String[]{transactionID});
            result = true;
        }
        cursor.close();

        return result;
    }
}
