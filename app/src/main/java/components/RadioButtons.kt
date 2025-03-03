package components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectable
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.sosauce.cutecalc.logic.PreferencesKeys
import com.sosauce.cutecalc.logic.dataStore
import com.sosauce.cutecalc.logic.saveTheme
import com.sosauce.cutecalc.ui.theme.GlobalFont
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

@Composable
fun ThemeRadioButtons() {
    val options = listOf("Dark", "Light", "Amoled")
    val context = LocalContext.current
    val dataStore: DataStore<Preferences> = context.dataStore
    val themeFlow: Flow<String?> = dataStore.data
        .map { preferences ->
            preferences[PreferencesKeys.THEME]
        }
    val theme by themeFlow.collectAsState(initial = null)

    Column(
        verticalArrangement = Arrangement.Center
    ) {
        options.forEach { option ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(56.dp)
                    .selectable(
                        selected = theme == option,
                        onClick = {
                            CoroutineScope(Dispatchers.IO).launch {
                                saveTheme(dataStore, option)
                            }
                        }
                    )
                    .padding(horizontal = 16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                RadioButton(
                    selected = theme == option,
                    onClick = null
                )
                Text(
                    text = option,
                    modifier = Modifier.padding(start = 16.dp),
                    fontFamily = GlobalFont
                )
            }
        }
    }
}


