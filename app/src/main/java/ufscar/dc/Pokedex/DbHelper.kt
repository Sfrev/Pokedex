package ufscar.dc.Pokedex

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.lang.Exception

class DbHelper(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION){
    companion object{
        private const val DB_VERSION = 1
        private const val DB_NAME = "my_pokemons"
        private const val TABLE_NAME = "pokemons"
        private const val FIELD_ID = "id"
        private const val SQL_CREATE =
            "CREATE TABLE ${TABLE_NAME} (" +
                    "${FIELD_ID} INTEGER PRIMARY KEY)"
        private const val SQL_DELETE =
            "DROP TABLE IF EXISTS ${TABLE_NAME}"

    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase, p1: Int, p2: Int) {
        db.execSQL(SQL_DELETE)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun insertPoke(id: Int): Long{
        try{

            val db = this.writableDatabase

            val contentValues = ContentValues()
            contentValues.put(FIELD_ID, id)

            val success = db.insert(TABLE_NAME, null, contentValues)
            db.close()

            return success
        }
        catch (e: Exception){
            println("SQL INSERT ERROR: " + e.message.toString())
        }

        return -1

    }

    fun deletePoke(id: Int): Int{
        try {

            val db = this.writableDatabase
            val deleteRows = db.delete(TABLE_NAME, "id = ?", arrayOf(id.toString()))

            db.close()

            return deleteRows
        }
        catch (e: Exception){
            println("SQL DEL ERROR: " + e.message.toString())
        }
        return -1
    }

    @SuppressLint("Range")
    fun getAllPoke(): ArrayList<Int>{
        val pokeList = ArrayList<Int>()
        val selectQuery = "SELECT * FROM ${TABLE_NAME}"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }
        catch (e: Exception){
            println("SQL ALL ERROR: " + e.message.toString())
            return ArrayList()
        }

        if(cursor.moveToFirst()){
            do {
                val id = cursor.getInt(cursor.getColumnIndex("id"))

                pokeList.add(id)
            }while (cursor.moveToNext())
        }

        return pokeList
    }


}