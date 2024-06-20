package furns.dao;

import furns.entity.Furn;

import java.util.List;

public interface FurnDAO {
    //返回所有的家居信息
    public List<Furn> queryFurn();

    //添加家具对象到数据库
    public int addFurn(Furn furn);

    //删除家居
    public int deleteFurnById(Integer id);

    //提供id，返回对应的家居对象
    public Furn queryFurnById(Integer id);

    //提供家居对象，修改家居的数据
    public int updateFurn(Furn furn);

    //提供名字，修改对应的家具信息
    public int changeSalesByName(String name, int sale);

    public int changeStockByName(String name, int stock);


    /**
     * Page的哪些属性是可以直接从数据库在获取的，就把它放在DAO层
     */
    //得到总共的记录数
    public int getTotalRow();

    //得到当前页的所有对象信息
    public List<Furn> getPageItem(int begin, int pageSize); //索引从零开始计算的

    //提供名字,返回对应的家具对象
    public List<Furn> getPageItemByName(String name, int begin, int pageSize);

    //提供名字，返回对应所有家居的数量
    public int getTotalRowByName(String name);

}
