package com.example.madpractical7

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "testandroid"
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(PersonDbTableData.CREATE_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS ${PersonDbTableData.TABLE_NAME}")
        onCreate(db)
    }

    fun insertPerson(name: String, emailId: String, phoneNo: String, address: String): Long {
        val values = ContentValues().apply {
            put(PersonDbTableData.COLUMN_PERSON_NAME, name)
            put(PersonDbTableData.COLUMN_PERSON_EMAIL_ID, emailId)
            put(PersonDbTableData.COLUMN_PERSON_PHONE_NO, phoneNo)
            put(PersonDbTableData.COLUMN_PERSON_ADDRESS, address)
        }
        return writableDatabase.insert(PersonDbTableData.TABLE_NAME, null, values)
    }

    fun getAllPersons(): List<Person> {
        val list = mutableListOf<Person>()
        val db = readableDatabase
        val cursor = db.query(
            PersonDbTableData.TABLE_NAME,
            null, null, null, null, null, null
        )
        cursor.use {
            while (it.moveToNext()) {
                val id = it.getInt(it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_ID)).toString()
                val name = it.getString(it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_NAME))
                val email = it.getString(it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_EMAIL_ID))
                val phone = it.getString(it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_PHONE_NO))
                val address = it.getString(it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_ADDRESS))
                val lat = getDoubleOrNull(it, it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_GPS_LAT))
                val lon = getDoubleOrNull(it, it.getColumnIndexOrThrow(PersonDbTableData.COLUMN_PERSON_GPS_LONG))
                list.add(Person(id, name, email, phone, address, lat, lon))
            }
        }
        return list
    }

    fun deletePerson(id: String): Boolean {
        val db = writableDatabase
        val result = db.delete(
            PersonDbTableData.TABLE_NAME,
            "${PersonDbTableData.COLUMN_ID} = ?",
            arrayOf(id)
        )
        return result > 0
    }

    private fun getDoubleOrNull(cursor: Cursor, idx: Int): Double? =
        if (cursor.isNull(idx)) null else cursor.getString(idx)?.toDoubleOrNull()
}

object PersonDbTableData {
    const val TABLE_NAME = "persons"
    const val COLUMN_ID = "id"
    const val COLUMN_PERSON_NAME = "name"
    const val COLUMN_PERSON_EMAIL_ID = "emailid"
    const val COLUMN_PERSON_PHONE_NO = "phoneno"
    const val COLUMN_PERSON_ADDRESS = "address"
    const val COLUMN_PERSON_GPS_LAT = "lat"
    const val COLUMN_PERSON_GPS_LONG = "long"

    val CREATE_TABLE = """
        CREATE TABLE $TABLE_NAME (
            $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
            $COLUMN_PERSON_NAME TEXT,
            $COLUMN_PERSON_EMAIL_ID TEXT,
            $COLUMN_PERSON_PHONE_NO TEXT,
            $COLUMN_PERSON_ADDRESS TEXT,
            $COLUMN_PERSON_GPS_LAT TEXT DEFAULT NULL,
            $COLUMN_PERSON_GPS_LONG TEXT DEFAULT NULL
        )
    """.trimIndent()
}
