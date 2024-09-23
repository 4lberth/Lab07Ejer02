package com.example.lab07ejer02

import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase

@Entity
data class Payment(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "card_number") val cardNumber: String,
    @ColumnInfo(name = "expiry_date") val expiryDate: String,
    @ColumnInfo(name = "cvv") val cvv: String,
    @ColumnInfo(name = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") val lastName: String,
    @ColumnInfo(name = "email") val email: String
)

@Dao
interface PaymentDao {
    @Query("SELECT * FROM Payment")
    suspend fun getAll(): List<Payment>

    @Insert
    suspend fun insert(payment: Payment)

    @Delete
    suspend fun delete(payment: Payment)
}


@Database(entities = [Payment::class], version = 1)
abstract class PaymentDatabase: RoomDatabase() {
    abstract fun paymentDao(): PaymentDao
}

