package com.connectandheal.hrmsdk.view.theme.hrm.screens.common.heartratemeasure

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.Activities
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey200
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryCharcoalGray
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TertiaryPastelViolet
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size12_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size18_Weight700
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size34_Weight700

@Composable
fun ActivitySheetContent(
    categoryList: List<Activities>,
     onAction: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier.padding(top = 20.dp),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(id = R.drawable.ic_heart),
                contentDescription = "instructions",
                modifier = Modifier
                    .padding(start = 6.dp)
                    .size(43.dp)
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "42", style = TextStyle_Size34_Weight700.copy(lineHeight = 40.sp),
                    color = Grey200
                )
                Text(
                    text = "BPM",
                    style = TextStyle_Size12_Weight400.copy(lineHeight = 13.sp),
                    color = Grey200,
                    textAlign = TextAlign.Center
                )
            }
        }
        Text(
            text = "To get more insights, select what you were doing in the last 15 minutes?",
            style = TextStyle_Size18_Weight700,
            color = PrimarySolidBlue,
            textAlign = TextAlign.Start,
            modifier = Modifier.padding(vertical = 32.dp, horizontal = 16.dp)
        )
        ActivitiesGrid(onAction = onAction, categoryList)
    }
}

@Composable
fun ActivitiesGrid(
    onAction: () -> Unit,
    categoryList: List<Activities>
) {
    val groupedCategories = categoryList.chunked(3)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .padding(top = 26.dp)
            .padding(horizontal = 12.dp)
    ) {
        groupedCategories.forEachIndexed { index, rowItems ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                modifier = Modifier.fillMaxWidth(rowItems.count().toFloat() / 3),
            ) {
                for (columnIndex in 0 until rowItems.count()) {
                    Box(
                        modifier = Modifier.weight(1F, fill = true),
                        propagateMinConstraints = true,
                    ) {
                        val item = rowItems[columnIndex]
                        HealthAndWellnessCategoryItem(activity = item, onAction = onAction)
                    }
                }
            }
            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HealthAndWellnessCategoryItem(
    activity: Activities, onAction: () -> Unit
) {
    Card(
        shape = RoundedCornerShape(6.dp),
        modifier = Modifier
            .height(157.dp)
            .width(133.dp)
            .clickable(
                onClick = onAction,
                interactionSource = MutableInteractionSource(),
                indication = null
            ),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent, contentColor = Color.Transparent
        )
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
                .background(color = TertiaryPastelViolet),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painter = painterResource(id = activity.image),
                modifier = Modifier.size(64.dp),
                contentDescription = "activity",
                alignment = Alignment.TopCenter,
            )
            Text(
                text = activity.activity.name,
                style = TextStyle_Size12_Weight700.copy(lineHeight = 19.sp),
                modifier = Modifier
                    .padding(all = 12.dp),
                color = PrimaryCharcoalGray,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}