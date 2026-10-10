package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.CameraAlt
import androidx.compose.material.icons.filled.CleaningServices
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material.icons.filled.VpnKey
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.model.BrowserTabItem
import com.example.ui.theme.ActiveGreen
import com.example.ui.theme.DarkNavyBackground
import com.example.ui.theme.DarkNavyBorder
import com.example.ui.theme.DarkNavySurface
import com.example.ui.theme.ElectricBlue
import com.example.ui.theme.TextWhite

/**
 * Universal Top Control Bar:
 * Displays:
 * [ Cookies (Icon Only) ] | [ Accounts/Instagram Toggle ] | [ ON/OFF ] | [ Clear ] | [ Mail ] | [ Next ] | [ Reload ]
 */
@Composable
fun UniversalTopControlBar(
    isAutomationRunning: Boolean,
    isAccountsCenterTarget: Boolean,
    openTabs: List<BrowserTabItem> = emptyList(),
    activeTabId: Int = 1,
    onSelectTab: (Int) -> Unit = {},
    onCloseTab: (Int) -> Unit = {},
    onNewTab: () -> Unit = {},
    onCookieClick: () -> Unit,
    onToggleNav: () -> Unit,
    onToggleAutomation: () -> Unit,
    onClearClick: () -> Unit,
    onMailClick: () -> Unit,
    onNextClick: () -> Unit,
    onReloadClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Surface(
        modifier = modifier
            .fillMaxWidth()
            .statusBarsPadding(),
        color = DarkNavyBackground,
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp, vertical = 6.dp)
        ) {
            if (openTabs.isNotEmpty()) {
                BrowserTabBar(
                    openTabs = openTabs,
                    activeTabId = activeTabId,
                    onSelectTab = onSelectTab,
                    onCloseTab = onCloseTab,
                    onNewTab = onNewTab,
                    titlePrefix = "Tab"
                )
                Spacer(modifier = Modifier.height(4.dp))
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 1. [ Cookies ] - ICON ONLY (no "Cookies" text)
                TopActionIconButton(
                    icon = Icons.Default.VpnKey,
                    accentColor = ElectricBlue,
                    onClick = onCookieClick,
                    contentDescription = "Cookies",
                    modifier = Modifier.testTag("top_bar_cookies_button")
                )

                // 2. [ Accounts Center <-> Instagram Navigation Toggle ]
                val navColor = if (isAccountsCenterTarget) Color(0xFF00E5FF) else Color(0xFFE1306C)
                val navBg = if (isAccountsCenterTarget) Color(0xFF00E5FF).copy(alpha = 0.15f) else Color(0xFFE1306C).copy(alpha = 0.15f)
                val navBorder = navColor
                val navIcon = if (isAccountsCenterTarget) Icons.Default.ManageAccounts else Icons.Default.CameraAlt
                val navLabel = if (isAccountsCenterTarget) "Center" else "Insta"

                Box(
                    modifier = Modifier
                        .weight(1.05f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(navBg)
                        .border(1.2.dp, navBorder, RoundedCornerShape(8.dp))
                        .clickable { onToggleNav() }
                        .padding(horizontal = 2.dp)
                        .testTag("top_bar_toggle_nav_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = navIcon,
                            contentDescription = navLabel,
                            tint = navColor,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = navLabel,
                            color = navColor,
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1
                        )
                    }
                }

                // 3. [ ON/OFF ]
                val onOffColor = if (isAutomationRunning) ActiveGreen else Color(0xFFFF5252)
                val onOffBg = if (isAutomationRunning) ActiveGreen.copy(alpha = 0.25f) else DarkNavySurface
                val onOffBorder = if (isAutomationRunning) ActiveGreen else DarkNavyBorder
                val onOffIcon = if (isAutomationRunning) Icons.Default.Stop else Icons.Default.PlayArrow
                val onOffLabel = if (isAutomationRunning) "ON" else "OFF"

                Box(
                    modifier = Modifier
                        .weight(0.95f)
                        .height(44.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(onOffBg)
                        .border(1.5.dp, onOffBorder, RoundedCornerShape(8.dp))
                        .clickable { onToggleAutomation() }
                        .padding(horizontal = 2.dp)
                        .testTag("top_bar_on_off_button"),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Icon(
                            imageVector = onOffIcon,
                            contentDescription = onOffLabel,
                            tint = onOffColor,
                            modifier = Modifier.size(15.dp)
                        )
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(
                            text = onOffLabel,
                            color = onOffColor,
                            fontSize = 11.5.sp,
                            fontWeight = FontWeight.ExtraBold,
                            letterSpacing = 0.5.sp
                        )
                    }
                }

                // 4. [ Clear ]
                TopActionButton(
                    label = "Clear",
                    icon = Icons.Default.CleaningServices,
                    accentColor = Color(0xFFFF9100),
                    onClick = onClearClick,
                    modifier = Modifier
                        .weight(0.95f)
                        .testTag("top_bar_clear_button")
                )

                // 5. [ Mail ]
                TopActionButton(
                    label = "Mail",
                    icon = Icons.Default.Email,
                    accentColor = Color(0xFF00E5FF),
                    onClick = onMailClick,
                    modifier = Modifier
                        .weight(0.9f)
                        .testTag("top_bar_mail_button")
                )

                // 6. [ Next ]
                TopActionButton(
                    label = "Next",
                    icon = Icons.AutoMirrored.Filled.ArrowForward,
                    accentColor = Color(0xFF7C4DFF),
                    onClick = onNextClick,
                    modifier = Modifier
                        .weight(0.9f)
                        .testTag("top_bar_next_button")
                )

                // 7. [ Reload ] - Reload current active page
                TopActionIconButton(
                    icon = Icons.Default.Refresh,
                    accentColor = Color(0xFF00E5FF),
                    onClick = onReloadClick,
                    contentDescription = "Reload",
                    modifier = Modifier.testTag("top_bar_reload_button")
                )
            }
        }
    }
}

@Composable
fun TopActionIconButton(
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    contentDescription: String,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .width(38.dp)
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(DarkNavySurface)
            .border(1.dp, DarkNavyBorder, RoundedCornerShape(8.dp))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = icon,
            contentDescription = contentDescription,
            tint = accentColor,
            modifier = Modifier.size(17.dp)
        )
    }
}

@Composable
fun TopActionButton(
    label: String,
    icon: ImageVector,
    accentColor: Color,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(DarkNavySurface)
            .border(1.dp, DarkNavyBorder, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(horizontal = 2.dp),
        contentAlignment = Alignment.Center
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                tint = accentColor,
                modifier = Modifier.size(13.dp)
            )
            Spacer(modifier = Modifier.width(2.dp))
            Text(
                text = label,
                color = TextWhite,
                fontSize = 11.sp,
                fontWeight = FontWeight.Bold,
                maxLines = 1
            )
        }
    }
}
