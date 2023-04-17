package com.example.assignment


import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import com.google.firebase.firestore.Blob
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

// ============================================================================
// Entity Classes
// ============================================================================
@Entity
data class Address (
    @DocumentId
    @PrimaryKey(autoGenerate = true)
    var id   : Int = 0,
    var name : String,
    var phoneNo: String,
    var address: String,
){
    @get:Exclude
    var isSelected: Boolean = false
}

data class Customer(
    @DocumentId
    var id   : String = "",
    var name : String = "",
    var email: String = "",
    var password: String = "",
    var balance: Double = 0.00,
    var coin : Int = 0,
    var deliveryAddress: String = ""
)

data class OrderHis(
    @DocumentId
    var id   : String = "",
    var product : String = "",
    var date: String = "",
    var rating: String = "",
    var comment: String = "",
    var customerId: String = "",
){
    @get:Exclude
    var customer: Customer = Customer()
}


data class Category(
    @DocumentId
    var  id: String = "",
    var name: String = "",
){
    // TODO(1): Additional field: [count] and [toString]
    @get:Exclude
    var count: Int = 0
    override fun toString() = name  //Spinner
}

data class Product(
    @DocumentId
    var  id: String = "",
    var name: String = "",
    var price: Double = 0.00,
    var categoryId: String = "",
    var stock:Int = 0,
    var photo: Blob = Blob.fromBytes(ByteArray(0)),
){
    // TODO(2): Additional field: [category]
    @get:Exclude
    var category: Category = Category()
}

data class Cart(
    @DocumentId
    var id: String = "",
    var customerId: String = "",
    var categoryId: String = "",
    var price: Double = 0.00,
    var product: String = "",
    var quantity: Int = 0,
    var photo: Blob = Blob.fromBytes(ByteArray(0))
)
data class Vouchers(
    @DocumentId
    var id: String = "",
    var minspend: Double = 0.00,
    var type: String = "",
    var value: Double = 0.00,
)



val CATEGORIES = Firebase.firestore.collection("categories")
val PRODUCTS = Firebase.firestore.collection("products")
val CART = Firebase.firestore.collection("cart")
val VOUCHERS = Firebase.firestore.collection("vouchers")


//
val CUSTOMER = Firebase.firestore.collection("customer")
val ORDERHIS = Firebase.firestore.collection("orderHis")


fun RESTORE_DATA(){
    //write into firestore
    val orderHis = listOf(
        OrderHis("A001","Nescafe Latte 240 ml","1/10/2022","4","Good","C00001"),
        OrderHis("A002","Maggi Chili Sauce 500g","1/10/2022","3","Not Bad","C00001"),
        OrderHis("A003","Oyoshi Green Tea Honey Lemon 380ml","5/10/2022","1","Bad","C00002"),
        OrderHis("A004","Spritzer Mineral Water 550ml","3/10/2022","2","No Good","C00001"),
        OrderHis("A005","Mi Sedap Mi Goreng Asli (5 x 90g)","6/10/2022","5","Very Nice","C00002"),
        OrderHis("A006","Maggi Tomato Ketchup 325g","6/10/2022","","","C00002"),
        OrderHis("A007","Dettol Hand Sanitizer Original 50ml","9/10/2022","","","C00002"),
        OrderHis("A008","Snickers Peanut Chocloate Bar 51g","4/10/2022","","","C00001"),
        OrderHis("A009","Twisties BBQ Curry 60g","2/10/2022","","","C00001"),
    )

    for(o in orderHis){
        ORDERHIS.document(o.id).set(o)
    }
}





// ============================================================================
// Data Access Objects (DAO)
// ============================================================================
@Dao
interface AddressDao{
    @Query("SELECT * FROM address")
    fun getAll(): LiveData<List<Address>>

    @Query("SELECT * FROM address WHERE id = :id")
    fun get(id: Int): LiveData<Address>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(f: Address)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg f: Address)

    @Update
    suspend fun update(f: Address)

    @Delete
    suspend fun delete(f: Address)

    @Query("DELETE FROM address")
    suspend fun deleteAll()
}


// ============================================================================
// Database
// ============================================================================
@Database(
    entities = [Address::class],
    version = 1,
    exportSchema = false
)
abstract class DB : RoomDatabase(){
    abstract val addressDao: AddressDao

    //Singleton = a class can have a most one object
    companion object{
        @Volatile
        var instance: DB? = null

        @Synchronized
        fun getInstance(context: Context): DB{
            instance = instance ?: Room.databaseBuilder(context, DB::class.java, "database.db")
                .fallbackToDestructiveMigration().build()
            //if instance is null then create a database
            return instance!! //!! = not null
        }
    }
}