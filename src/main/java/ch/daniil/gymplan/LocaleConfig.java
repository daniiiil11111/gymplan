package ch.daniil.gymplan;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AbstractLocaleResolver;

import java.util.Locale;

/**
 * The language of the result page is the plan language chosen in the form.
 * The form posts a field called "language", so every request that carries it is rendered in that language.
 */
@Configuration
public class LocaleConfig {

    @Bean
    public LocaleResolver localeResolver() {
        return new AbstractLocaleResolver() {
            @Override
            public Locale resolveLocale(HttpServletRequest request) {
                String language = request.getParameter("language");
                if ("de".equals(language) || "uk".equals(language)) {
                    return Locale.forLanguageTag(language);
                }
                return Locale.ENGLISH;
            }

            @Override
            public void setLocale(HttpServletRequest request, HttpServletResponse response, Locale locale) {
                // Not needed: the language comes from the form field on every request.
            }
        };
    }
}
