package furns.service.impl;

import furns.dao.FurnDAO;
import furns.dao.impl.FurnDAOImpl;
import furns.entity.Furn;
import furns.entity.Page;
import furns.service.FurnService;

import java.util.List;

public class FurnServiceImpl implements FurnService {
    private FurnDAO furnDAO = new FurnDAOImpl();

    @Override
    public List<Furn> queryFurns() {
        return furnDAO.queryFurn();
    }

    @Override
    public int addFurn(Furn furn) {
        return furnDAO.addFurn(furn);
    }

    @Override
    public int deleteFurnById(Integer id) {
        return furnDAO.deleteFurnById(id);
    }

    @Override
    public Furn queryFurnById(Integer id) {
        return furnDAO.queryFurnById(id);
    }

    @Override
    public int updateFurn(Furn furn) {
        return furnDAO.updateFurn(furn);
    }


    /**
     * 配置Page参数：
     * pageNo (传入)
     * pageSize (传入)
     * TotalRow (DAO层获取)
     * pageTotalCount (计算)
     * pageItems (传入当前页的起始索引和pageSize，然后DAO层获取)
     */
    @Override
    public Page<Furn> page(int pageNo, int pageSize) {
        //创建Page对象，然后填充数据
        Page<Furn> page = new Page<>();

        //设置当前页
        page.setPageNo(pageNo);

        //设置每页的记录数
        page.setPageSize(pageSize);

        //从DAO层获取记录总数
        int totalRow = furnDAO.getTotalRow();
        page.setTotalRow(totalRow);

        //计算得出一共需要的页数
        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            pageTotalCount += 1;
        }
        page.setPageTotalCount(pageTotalCount);

        //得到当前页的所有对象信息，根据指定的pageNo计算begin索引值，传入getPageItem方法
        int begin = (pageNo - 1) * pageSize;
        List<Furn> pageItems = furnDAO.getPageItem(begin, pageSize);
        page.setItems(pageItems);

        return page;
    }

    @Override
    public Page<Furn> pageByName(String name, int pageNo, int pageSize) {
        Page<Furn> page = new Page<>();

        page.setPageNo(pageNo);

        page.setPageSize(pageSize);

        int totalRow = furnDAO.getTotalRowByName(name);
        page.setPageTotalCount(totalRow);

        int pageTotalCount = totalRow / pageSize;
        if (totalRow % pageSize != 0) {
            pageTotalCount += 1;
        }
        page.setPageTotalCount(pageTotalCount);

        int begin = (pageNo - 1) * pageSize;
        page.setItems(furnDAO.getPageItemByName(name, begin, pageSize));

        return page;
    }
}
