package com.connectandheal.hrmsdk.view.theme.hrm.screens.common

import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.connectandheal.hrmsdk.view.theme.hrm.theme.BorderActive
import com.connectandheal.hrmsdk.view.theme.hrm.theme.BorderLight
import com.soscare.customer.view.common.theme.CnHTheme

// TODO: Remove/split this into functional types with defaults. What is the purpose/responsibility of this class?
@Composable
private fun OutlinedTextField(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    outerLabel: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorIcon: @Composable (() -> Unit)? = null,
    helperMessage: @Composable (() -> Unit)? = null,
    errorMessage: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(
        focusedIndicatorColor = BorderActive,
        unfocusedIndicatorColor = BorderLight,
        containerColor = Color.Transparent,
        //cursorColor = LocalContentColor.current.copy(alpha = LocalContentAlpha.current)
    ),

    ) {
    Column(
        modifier = modifier
    ) {

        outerLabel?.let { it() }
        androidx.compose.material3.OutlinedTextField(
            value = value,
            modifier = modifier,
            onValueChange = onValueChange,
            enabled = enabled,
            readOnly = readOnly,
            textStyle = textStyle,
            label = label,
            placeholder = placeholder,
            leadingIcon = leadingIcon,
            trailingIcon = if (isError) errorIcon else trailingIcon,
            isError = isError,
            visualTransformation = visualTransformation,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            singleLine = singleLine,
            maxLines = maxLines,
            interactionSource = interactionSource,
            shape = shape,
            colors = colors,

            )
        Box(
            modifier = Modifier
                .requiredHeight(16.dp)
                .padding(start = 16.dp, end = 12.dp)

        ) {
            CompositionLocalProvider(
                LocalTextStyle provides LocalTextStyle.current.copy(
                    fontSize = 12.sp,
                    color = when {
                        isError -> MaterialTheme.colorScheme.error
                        else -> LocalTextStyle.current.color
                    }
                )
            ) {
                when {
                    isError -> {
                        if (errorMessage != null) {
                            errorMessage()
                        }
                    }
                    else -> {
                        if (helperMessage != null) {
                            run {
                                helperMessage()
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun OutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    outerLabel: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    errorIcon: @Composable (() -> Unit)? = null,
    helperMessage: @Composable (() -> Unit)? = null,
    errorMessage: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = MaterialTheme.shapes.small,
    colors: TextFieldColors = TextFieldDefaults.textFieldColors(),
) {

    var textFieldValueState by remember { mutableStateOf(TextFieldValue(text = value)) }
    val textFieldValue = textFieldValueState.copy(text = value)

    OutlinedTextField(
        value = textFieldValue,
        onValueChange = {
            textFieldValueState = it
            if (value != it.text) {
                onValueChange(it.text)
            }
        },
        modifier = modifier,
        enabled = enabled,
        readOnly = readOnly,
        singleLine = singleLine,
        textStyle = textStyle,
        label = label,
        outerLabel = outerLabel,
        placeholder = placeholder,
        leadingIcon = leadingIcon,
        trailingIcon = trailingIcon,
        errorIcon = errorIcon,
        helperMessage = helperMessage,
        errorMessage = errorMessage,
        isError = isError,
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        maxLines = maxLines,
        interactionSource = interactionSource,
        shape = shape,
        colors = TextFieldDefaults.textFieldColors(
            containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
        ),
    )
}

@Preview(name = "Day ꞏ Helper", showBackground = true)
@Composable
fun OutlinedTextFieldDayHelperPreview() {
    CnHTheme {
        Surface(modifier = Modifier.padding(15.dp, 0.dp)) {
            OutlinedTextField(
                value = TextFieldValue(""),
                onValueChange = { },
                trailingIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.AccountCircle),
                        contentDescription = null
                    )
                },
                errorIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Rounded.AccountCircle),
                        contentDescription = null
                    )
                },
                helperMessage = {
                    Text(text = "This is an helper!")
                }
            )
        }
    }
}

@Preview(name = "Day ꞏ Error", showBackground = true)
@Composable
fun OutlinedTextFieldNightHelperPreview() {
    CnHTheme {
        Surface(modifier = Modifier.padding(15.dp, 0.dp)) {
            OutlinedTextField(
                value = TextFieldValue(),
                onValueChange = { },
                errorMessage = {
                    Text(text = "This is an error!")
                },
                errorIcon = {
                    Icon(
                        painter = rememberVectorPainter(image = Icons.Default.Person),
                        contentDescription = null
                    )
                },
                isError = true
            )
        }
    }
}

/**
 * To be removed when [TextField]s support error
 */
@Composable
fun TextFieldError(textError: String) {
    Row(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = textError,
            modifier = Modifier.fillMaxWidth(),
            style = LocalTextStyle.current.copy(color = MaterialTheme.colorScheme.error)
        )
    }
}

@Composable
fun CNHOutlinedTextField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyle = LocalTextStyle.current,
    label: @Composable (() -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    trailingIcon: @Composable (() -> Unit)? = null,
    isError: Boolean = false,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    singleLine: Boolean = false,
    maxLines: Int = Int.MAX_VALUE,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    shape: Shape = RoundedCornerShape(2.dp),
    colors: TextFieldColors = TextFieldDefaults.outlinedTextFieldColors()
) {
    // If color is not provided via the text style, use content color as a default
    val textColor = textStyle.color.takeOrElse {
        colors.textColor(enabled).value
    }
    val mergedTextStyle = textStyle.merge(TextStyle(color = textColor))

    @OptIn(ExperimentalMaterial3Api::class)
    (BasicTextField(
        value = value,
        modifier = modifier
            .background(colors.containerColor(enabled).value, shape),
        onValueChange = onValueChange,
        enabled = enabled,
        readOnly = readOnly,
        textStyle = mergedTextStyle,
        cursorBrush = SolidColor(colors.cursorColor(isError).value),
        visualTransformation = visualTransformation,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        interactionSource = interactionSource,
        singleLine = singleLine,
        maxLines = maxLines,
        decorationBox = @Composable { innerTextField ->
            TextFieldDefaults.OutlinedTextFieldDecorationBox(
                value = value,
                visualTransformation = visualTransformation,
                innerTextField = innerTextField,
                placeholder = placeholder,
                label = label,
                leadingIcon = leadingIcon,
                trailingIcon = trailingIcon,
                singleLine = singleLine,
                enabled = enabled,
                isError = isError,
                interactionSource = interactionSource,
                colors = colors,
                border = {
                    TextFieldDefaults.BorderBox(
                        enabled,
                        isError,
                        interactionSource,
                        colors,
                        shape
                    )
                }
            )
        }
    ))
}


