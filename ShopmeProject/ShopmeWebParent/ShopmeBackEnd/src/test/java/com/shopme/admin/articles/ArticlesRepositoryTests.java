package com.shopme.admin.articles;

import com.shopme.admin.article.ArticleRepository;
import com.shopme.common.entity.Article;
import com.shopme.common.entity.ArticleType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class ArticlesRepositoryTests {
    @Autowired
    private ArticleRepository repo;

    @Test
    public void testListMenuBoundArticles() {
        List<Article> listArticles = repo.findByTypeOrderByTitle(ArticleType.MENU_BOUND);
        assertThat(listArticles).isNotEmpty();

        listArticles.forEach(System.out::println);
    }
}
