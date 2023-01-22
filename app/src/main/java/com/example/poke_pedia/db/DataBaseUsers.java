package com.example.poke_pedia.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.poke_pedia.model.Pokemon;
import com.example.poke_pedia.model.SerializableBitMap;
import com.example.poke_pedia.model.Stat;
import com.example.poke_pedia.model.Stats;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.List;

public class DataBaseUsers extends SQLiteOpenHelper {
    public static final String DBNAME = "prueba9.db";
    public DataBaseUsers(Context context) {
        super(context, DBNAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase MyDB) {
        MyDB.execSQL("CREATE TABLE users(username TEXT PRIMARY KEY, password TEXT)");
        MyDB.execSQL("CREATE TABLE pokemons(id INT PRIMARY KEY, name TEXT, image BLOB)");
        MyDB.execSQL("CREATE TABLE stats(id INT, stat TEXT, base INT, PRIMARY KEY(id,stat,base))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase MyDB, int i, int i1) {
        MyDB.execSQL("DROP TABLE IF EXISTS users");
        MyDB.execSQL("DROP TABLE IF EXISTS pokemons");
        MyDB.execSQL("DROP TABLE IF EXISTS stats");
    }

    public boolean insertData(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues= new ContentValues();
        contentValues.put("username", username);
        contentValues.put("password", password);
        long result = MyDB.insert("users", null, contentValues);
        if(result==-1) return false;
        else
            return true;
    }

    public boolean checkUsername(String username) {
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        if (cursor.getCount() > 0)
            return true;
        else
            return false;
    }

    public boolean checkUserPass(String username, String password){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        Cursor cursor = MyDB.rawQuery("SELECT * FROM users WHERE username = ? AND password = ?", new String[] {username,password});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public boolean checkUserExists(String username){
        SQLiteDatabase myDB = this.getWritableDatabase();
        Cursor cursor = myDB.rawQuery("SELECT * FROM users WHERE username = ?", new String[]{username});
        if(cursor.getCount()>0)
            return true;
        else
            return false;
    }

    public void changePassword(String username, String pass){
        SQLiteDatabase myDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("password",pass);
        int res = myDB.update("users",contentValues,"username=?",new String[]{username});
    }

    public boolean insertPokemon(Pokemon p){
        SQLiteDatabase MyDB = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("id", p.getId());
        contentValues.put("name", p.getName());
        contentValues.put("image", SerializableBitMap.getBytes(p.getImage().getBitmap()));
        long result = MyDB.insert("pokemons",null,contentValues);
        if(result==-1) return false;
        for(Stats s : p.getStats()){
            contentValues = new ContentValues();
            contentValues.put("id",p.getId());
            contentValues.put("stat",s.getStat().getName());
            contentValues.put("base",s.getBase_stat());
            result = MyDB.insert("stats",null,contentValues);
            if(result==-1) return false;
        }
        return true;
    }

    public List<Pokemon> getPokemons(){

        List<Pokemon> res = new ArrayList<>();

        SQLiteDatabase myDB = this.getWritableDatabase();
        String query = "SELECT * FROM pokemons " +
                "INNER JOIN stats ON pokemons.id == stats.id;";
        Cursor c = myDB.rawQuery(query,null);

        Pokemon p;

        while(c.moveToNext()){
            int id = c.getInt(0);
            String name = c.getString(1);
            byte[] image = c.getBlob(2);
            String stat = c.getString(4);
            int base = c.getInt(5);

            p = new Pokemon(id,name);

            int index = p.searchById(res,id);
            if(index==-1){
                //el pokemon todavia no esta en la lista
                p.setImage(new SerializableBitMap(
                        SerializableBitMap.
                                getImage(image)));
                List<Stats> listAux = new ArrayList<>();
                listAux.add(new Stats(base, new Stat(stat)));
                p.setStats(listAux);
                res.add(p);
            }
            else{
                p = res.get(index);
                List<Stats> listAux = p.getStats();
                listAux.add(new Stats(base, new Stat(stat)));
                p.setStats(listAux);
                res.set(index,p);
            }
        }

        return res;
    }
}