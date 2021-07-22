package ru.lesnikovaYana.util;

import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import ru.lesnikovaYana.db.dao.CategoriesMapper;
import ru.lesnikovaYana.db.dao.ProductsMapper;
import ru.lesnikovaYana.db.model.CategoriesExample;
import ru.lesnikovaYana.db.model.Products;
import ru.lesnikovaYana.db.model.ProductsExample;

@UtilityClass
public class DbUtils {
    private static  String resource = "mybatis-config.xml";

    @SneakyThrows
    private static SqlSession getSqlSession() {
        SqlSessionFactory sqlSessionFactory;
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(Resources.getResourceAsStream(resource));
        return sqlSessionFactory.openSession(true);
    }

    @SneakyThrows
    public static CategoriesMapper getCategoriesMapper(){
        return getSqlSession().getMapper(CategoriesMapper.class);
    }

    @SneakyThrows
    public static ProductsMapper getProductsMapper() {
        return getSqlSession().getMapper(ProductsMapper.class);
    }

    public static Integer countCategories(CategoriesMapper categoriesMapper) {
        long categoriesCount = categoriesMapper.countByExample(new CategoriesExample());
        return Math.toIntExact(categoriesCount);
    }

    public static Integer countProducts(ProductsMapper productsMapper) {
        long products = productsMapper.countByExample(new ProductsExample());
        return Math.toIntExact(products);
    }

    public static int deleteProductById(ProductsMapper productsMapper, long id){
        return productsMapper.deleteByPrimaryKey(id);
    }

    public static int updateProduct(ProductsMapper productsMapper, Products product){
        return productsMapper.updateByPrimaryKey(product);
    }
}
