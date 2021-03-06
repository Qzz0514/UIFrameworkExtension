package group.ACupOfJava.dao;

import group.ACupOfJava.pojo.ImageBox;
import group.ACupOfJava.pojo.Shop;
import group.ACupOfJava.pojo.User;
import org.apache.ibatis.annotations.Select;

import java.awt.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * ClassName:ShopDao
 * Packeage:group.ACupOfJava.dao
 *
 * @Date:2020/11/22 10:35
 */


public interface ShopDao {
    @Select("select * from shop")
    public List<Shop> shopList();

    //收藏的商店列表
    @Select("SELECT distinct shop.* from user,shop,collection where user.user_id = collection.user_id and shop.shop_id = collection.shop_id and user.user_id = #{id}")
    public List<Shop> myShopList(int id);

    public Shop shopDetail(int shop_id);

    public int addCollection(Map<String,Integer> map);
    public int updateStars(Map<String, Integer> map);
    public List<Shop> hotList();

<<<<<<< HEAD


=======
>>>>>>> 29b4465a9eced31f7d87ed69c4d0b00b790f556e
    public int addLikes(Map<String,Integer> map);
    public int updateLikes(Map<String, Integer> map);
    public List<Shop> recentList(int id);

<<<<<<< HEAD


    public List<Shop> talkList(List<String> list);
=======
    public List<Shop> talkList(List<String> list);


    public List<ImageBox> findbannerImagesById(int shop_id);
    public List<ImageBox> bannerImages(int shop_id);

    public List<Shop> selectCity(String location);


>>>>>>> 29b4465a9eced31f7d87ed69c4d0b00b790f556e



}
