package com.connectandheal.hrmsdk.view.theme.hrm.screens.hrminsights

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.R
import com.connectandheal.hrmsdk.domain.PreviousReadingItem
import com.connectandheal.hrmsdk.view.theme.hrm.theme.BorderActive
import com.connectandheal.hrmsdk.view.theme.hrm.theme.BorderLight
import com.connectandheal.hrmsdk.view.theme.hrm.theme.Grey100
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidBlue
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimarySolidGreen
import com.connectandheal.hrmsdk.view.theme.hrm.theme.PrimaryWhite
import com.connectandheal.hrmsdk.view.theme.hrm.theme.RedOrange
import com.connectandheal.hrmsdk.view.theme.hrm.theme.SecondaryPink
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size14_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight400
import com.connectandheal.hrmsdk.view.theme.hrm.theme.TextStyle_Size16_Weight700


@Composable
fun BottomSheetDeleteRow(
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable {
                onClick()
            }
            .padding(vertical = 20.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_hrm_trash),
                contentDescription = "",
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "Delete your records",
                style = TextStyle_Size16_Weight400,
                color = RedOrange
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun EditNote(
    previousReadingItem: PreviousReadingItem,
    onDelete: () -> Unit,
    onSave: () -> Unit,
    note: State<String>,
    onNoteChange: (String) -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        HeartRateReadingInformation(
            category = previousReadingItem.category,
            measuredOn = previousReadingItem.measuredOn,
            heartRate = previousReadingItem.heartRateValue,
            heartRateZone = previousReadingItem.heartRateZone,
            flowType = FlowType.BottomSheet
        )

        Spacer(modifier = Modifier.height(32.dp))

        OutlinedTextField(
            value = note.value,
            onValueChange = { newValue ->
                onNoteChange(newValue)
            },
            modifier = Modifier
                .fillMaxWidth()
                .wrapContentHeight(),
            placeholder = {
                Text(
                    text = "Add a note for your journal",
                    color = Grey100,
                    maxLines = 1,
                    style = TextStyle_Size14_Weight400.copy(lineHeight = 21.sp)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = BorderActive,
                unfocusedBorderColor = BorderLight
            ),
            maxLines = 3,
            shape = RoundedCornerShape(8.dp),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(onDone = {
                focusManager.clearFocus(true)
                keyboardController?.hide()
            })
        )
        Spacer(modifier = Modifier.height(32.dp))

        Row(modifier = Modifier.fillMaxWidth()) {
            BottomSheetButton(
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f),
                text = "Delete",
                contentColor = PrimarySolidBlue,
                containerColor = PrimaryWhite,
                borderStroke = BorderStroke(1.dp, SecondaryPink),
                onClick = {
                    //TODO
                }
            )
            Spacer(modifier = Modifier.width(32.dp))

            BottomSheetButton(
                modifier = Modifier
                    .height(48.dp)
                    .weight(1f),
                text = "Save",
                contentColor = PrimaryWhite,
                containerColor = PrimarySolidGreen,
                onClick = {
                    //TODO
                }
            )
        }
        Spacer(modifier = Modifier.height(32.dp))
    }
}

@Composable
fun BottomSheetButton(
    modifier: Modifier,
    text: String,
    contentColor: Color,
    containerColor: Color,
    borderStroke: BorderStroke? = null,
    onClick: () -> Unit
) {
    Button(
        modifier = modifier,
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(
            containerColor = containerColor,
            contentColor = contentColor
        ),
        border = borderStroke,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(
            text = text,
            style = TextStyle_Size16_Weight700.copy(lineHeight = 20.sp)
        )
    }
}
