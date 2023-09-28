package com.weatherapp.ui.cities

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weatherapp.R
import com.weatherapp.ui.theme.overPassFamily
import com.weatherapp.ui.weatherinfo.WeatherInfoViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CitiesBottomSheet(onItemClicked: (String) -> Unit, onDismiss: () -> Unit) {
    val modalBottomSheetState = rememberModalBottomSheetState()

    ModalBottomSheet(
        modifier = Modifier.testTag(stringResource(id = R.string.test_tag_cities_bottom_sheet)),
        onDismissRequest = { onDismiss() },
        sheetState = modalBottomSheetState,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        CityList(onItemClicked)
    }
}


@Composable
fun CityList(onItemClicked: (String) -> Unit) {
    val cities = WeatherInfoViewModel.usCities

    LazyColumn {
        items(cities.size) { index ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp, horizontal = 0.dp)
                    .clickable { onItemClicked.invoke(cities[index]) }
            ) {
                Text(
                    text = cities[index],
                    modifier = Modifier.padding(horizontal = 20.dp),
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontFamily = overPassFamily,
                        fontWeight = FontWeight(400),
                    )
                )
            }
        }
    }
}


@Composable
@Preview
fun CityListPreview() {
    CityList {

    }

}
