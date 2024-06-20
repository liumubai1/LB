package furns.dao.impl;

import furns.dao.BasicDAO;
import furns.dao.FurnDAO;
import furns.entity.Furn;

import java.util.List;

public class FurnDAOImpl extends BasicDAO<Furn> implements FurnDAO {

    @Override
    public List<Furn> queryFurn() {
        String sql = "SELECT id, name, maker, price, sales, stock, img_path imgPath  FROM furn;";
        return queryMulti(sql, Furn.class);
    }

    @Override
    public int addFurn(Furn furn) {
        String sql = "INSERT INTO furn VALUES (NULL,?,?,?,?,?,?)";
        return update(sql, furn.getName(), furn.getMaker(),
                furn.getPrice(), furn.getSales(), furn.getStock(), furn.getImgPath());
    }

    @Override
    public int deleteFurnById(Integer id) {
        String sql = "DELETE FROM furn WHERE id =?";
        return update(sql, id);
    }

    @Override
    public Furn queryFurnById(Integer id) {
        String sql = "SELECT id, name, maker, price, sales, stock, img_path imgPath FROM furn WHERE id = ?";
        return querySingle(sql, Furn.class, id);
    }

    @Override
    public int updateFurn(Furn furn) {
        String sql = "UPDATE furn SET name =?,maker=?,price=?,sales=?,stock=?,img_path=? WHERE id=?";
        return update(sql, furn.getName(), furn.getMaker(), furn.getPrice(), furn.getSales(), furn.getStock(), furn.getImgPath(), furn.getId());
    }

    @Override
    public int changeSalesByName(String name, int sale) {
        String sql = "UPDATE furn SET sales = ? WHERE name=?";
        return update(sql, sale, name);
    }

    @Override
    public int changeStockByName(String name, int stock) {
        String sql = "UPDATE furn SET stock = ? WHERE name=?";
        return update(sql, stock, name);
    }

    @Override
    public int getTotalRow() {
        String sql = "SELECT count(*) FROM furn";
        //return (Integer) queryScalar(sql);  会报cast异常
        return ((Number) queryScalar(sql)).intValue(); //Number是所有基本包装类的父类
    }

    @Override
    public List<Furn> getPageItem(int begin, int pageSize) {
        String sql = "SELECT id, name, maker, price, sales, stock, img_path imgPath FROM furn LIMIT ?,?";
        return queryMulti(sql, Furn.class, begin, pageSize);
    }

    @Override
    public List<Furn> getPageItemByName(String name, int begin, int pageSize) {
        String sql = "SELECT id, name, maker, price, sales, stock, img_path imgPath FROM furn WHERE name LIKE ? LIMIT ?,?";
        return queryMulti(sql, Furn.class, "%" + name + "%", begin, pageSize);
    }

    @Override
    public int getTotalRowByName(String name) {
        String sql = "SELECT count(*) FROM furn WHERE name LIKE ?";
        return ((Number) queryScalar(sql, "%" + name + "%")).intValue();
    }

}
