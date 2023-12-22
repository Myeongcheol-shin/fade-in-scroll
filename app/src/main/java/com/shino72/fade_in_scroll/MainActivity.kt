package com.shino72.fade_in_scroll

import android.graphics.drawable.Animatable
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.shino72.fade_in_scroll.ui.theme.FadeinscrollTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FadeinscrollTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    scrollItems()
                }
            }
        }
    }
}

@Composable
private fun scrollItems(modifier: Modifier = Modifier, itemList : List<String> = List(50){it -> it.toString()}){
    val scrollState = rememberLazyListState()
    LazyColumn(state = scrollState){
        itemsIndexed(itemList){index, item ->
            val animatableAlpha = remember { androidx.compose.animation.core.Animatable(0f) }
            val isVisable = remember{
                derivedStateOf {
                    scrollState.firstVisibleItemIndex <= index
                }
            }
            LaunchedEffect(isVisable.value){
                if(isVisable.value){
                    animatableAlpha.animateTo(
                        1f, animationSpec = tween(durationMillis = 1000)
                    )
                }
            }
            scrollItem(content = item, alpha = animatableAlpha.value)
        }
    }
}


@Composable
private fun scrollItem(modifier: Modifier = Modifier, content : String, alpha : Float){
    Row(
        modifier = modifier
            .fillMaxWidth()
            .background(color = Color.DarkGray.copy(alpha = alpha))
            .padding(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        Text(text = content,
            color = Color.White,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
            )
    }
}