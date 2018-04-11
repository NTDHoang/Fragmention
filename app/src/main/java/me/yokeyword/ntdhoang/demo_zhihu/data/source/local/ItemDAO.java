package me.yokeyword.ntdhoang.demo_zhihu.data.source.local;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import io.reactivex.Flowable;
import io.reactivex.Single;
import java.util.List;
import me.yokeyword.ntdhoang.demo_zhihu.data.entity.Item;

@Dao
public interface ItemDAO {
    @Query("SELECT * FROM Item")
    Flowable<List<Item>> getItems();

    @Query("SELECT * FROM Item WHERE id = :id ")
    Single<Item> getItemById(int id);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItem(Item Item);

    @Query("DELETE FROM Item")
    void deleteAllItems();
}
