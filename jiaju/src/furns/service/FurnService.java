package furns.service;

import furns.entity.Furn;
import furns.entity.Page;

import java.util.List;

public interface FurnService {

    //得到所有家居的集合
    public List<Furn> queryFurns();

    //添加家居
    public int addFurn(Furn furn);

    //删除家居
    public int deleteFurnById(Integer id);

    //根据id，返回对应的家居对象
    public Furn queryFurnById(Integer id);

    //修改家居
    public int updateFurn(Furn furn);

    //指定每页显示多少数据，和显示第几页，得到page分页对象
    public Page<Furn> page(int pageNo, int pageSize);

    //指定name，得到page分页对象
    public Page<Furn> pageByName(String name, int pageNo, int pageSize);
}
