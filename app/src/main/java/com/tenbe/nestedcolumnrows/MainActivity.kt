package com.tenbe.nestedcolumnrows

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.focusable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.tenbe.nestedcolumnrows.ui.theme.NestedcolumnrowsTheme

@ExperimentalCoilApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NestedcolumnrowsTheme {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    HomeScreen(testData)
                }
            }
        }
    }
}

data class Row(val title: String, val items: List<Item>)
data class Item(val title: String, val url: String)

val testData = mutableListOf<Row>().apply {
    var itemCount = 1
    repeat(20) { i ->
        val items = mutableListOf<Item>().apply {
            repeat(10) { j ->
                add(
                    Item(
                        title = "$itemCount",
                        url = if (j % 2 == 0) "https://moodup.team/wp-content/uploads/2021/01/android_03-1024x546.png"
                        else "https://miro.medium.com/max/585/1*6YEqQnWbqHR8fQBn4IGqlA.png"
                    )
                )

                itemCount++
            }
        }

        add(
            Row(
                title = "Row #$i",
                items = items
            )
        )
    }
}

@ExperimentalCoilApi
@Composable
fun HomeScreen(
    content: List<Row>
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { "Title" })
        }
    ) {

        val columnState = rememberLazyListState()

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            state = columnState,
        ) {
            items(content) { row ->
                Column {
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        text = row.title,
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp
                    )

                    Row(
                        modifier = Modifier
                            .horizontalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        row.items.forEach { item ->
                            Box {
                                Image(
                                    modifier = Modifier
                                        .size(132.dp)
                                        .clip(RoundedCornerShape(8.dp)),
                                    painter = rememberImagePainter(item.url),
                                    contentDescription = item.title,
                                    contentScale = ContentScale.Crop
                                )

                                Text(
                                    modifier = Modifier.clearAndSetSemantics {},
                                    text = item.title)
                            }
                        }
                    }
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Preview
@Composable
private fun HomeScreenPreview() {
    NestedcolumnrowsTheme {
        HomeScreen(testData)
    }
}