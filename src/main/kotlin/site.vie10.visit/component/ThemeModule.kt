package site.vie10.visit.component

import csstype.Color
import csstype.None
import csstype.pct
import csstype.px
import kotlinx.browser.document
import kotlinx.dom.appendElement
import kotlinx.js.jso
import mui.icons.material.Brightness4
import mui.icons.material.Brightness7
import mui.material.CssBaseline
import mui.material.ToggleButton
import mui.material.ToggleButtonGroup
import mui.material.Typography
import mui.material.styles.Theme
import mui.material.styles.ThemeProvider
import org.w3c.dom.HTMLMetaElement
import react.*
import react.dom.aria.ariaLabel
import site.vie10.visit.common.Themes
import site.vie10.visit.cookie.Cookie
import site.vie10.visit.util.wordFromContext

/**
 * @author vie10
 **/
typealias ThemeState = StateInstance<Theme>

val ThemeContext = createContext<ThemeState>()

val ThemeModule = FC<PropsWithChildren> { props ->
    val state = useState(
        if (ThemeCookie.value == "dark") Themes.Dark else Themes.Light
    )
    val (theme) = state
    setThemeColorMeta(theme.palette.primary.main)

    ThemeContext.Provider(state) {
        ThemeProvider {
            this.theme = theme

            CssBaseline()
            +props.children
        }
    }
}

val ModeToggle = FC<Props> {
    var theme by useContext(ThemeContext)
    ThemeCookie.value = if (theme === Themes.Dark) "dark" else "light"

    Typography {
        sx = jso {
            color = Color("text.secondary")
        }
        variant = "caption"
        gutterBottom = true

        +wordFromContext.mode.uppercase()
    }
    ToggleButtonGroup {
        sx = jso {
            width = 100.pct
        }
        value = theme
        exclusive = true
        onChange = { _, newValue ->
            if (newValue != null) {
                theme = newValue.unsafeCast<Theme>()
            }
        }
        ariaLabel = wordFromContext.mode

        ToggleButton {
            sx = jso {
                width = 100.pct
                textTransform = None.none
            }
            value = Themes.Light
            ariaLabel = wordFromContext.light

            Brightness7 {
                sx = jso {
                    marginRight = 5.px
                }
            }
            +wordFromContext.light
        }

        ToggleButton {
            sx = jso {
                width = 100.pct
                textTransform = None.none
            }
            value = Themes.Dark
            ariaLabel = wordFromContext.dark

            Brightness4 {
                sx = jso {
                    marginRight = 5.px
                }
            }
            +wordFromContext.dark
        }
    }
}

private fun setThemeColorMeta(color: String) {
    val head = document.head!!
    val element = head.children.namedItem("theme-color")
    if (element != null) {
        (element as HTMLMetaElement).setAttribute("content", color)
    } else {
        head.appendElement("meta") {
            setAttribute("name", "theme-color")
            setAttribute("content", color)
        }
    }
}

val ThemeCookie = Cookie("theme")
