package com.spotify.aaronfodor.gracefulshutdownissue

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextIndent
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.spotify.aaronfodor.gracefulshutdownissue.ui.theme.GracefulShutdownIssueTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        buildUi()
    }

    private fun buildUi() {
        setContent {
            GracefulShutdownIssueTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column{
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Text(text = "Graceful Shutdown issue", fontSize = 28.sp)
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Column(modifier =
                            Modifier.fillMaxWidth().padding(10.dp).border(BorderStroke(2.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Foreground service ✅", fontSize = 20.sp,
                                    modifier = Modifier.padding(10.dp))
                                Text(buildAnnotatedString {
                                    foregroundPoints.forEach {
                                        withStyle(style = paragraphStyle) {
                                            append(bullet)
                                            append("\t\t")
                                            append(it)
                                        }
                                    }
                                }, modifier = Modifier.padding(10.dp))
                                Button(
                                    onClick = {
                                        val serviceIntent = Intent(this@MainActivity, SomeService::class.java)
                                            .setAction(SomeService.CALL_START_FOREGROUND)
                                        applicationContext.startForegroundService(serviceIntent)
                                    }, modifier = Modifier.padding(10.dp)) { Text(text = "Start") }
                            }
                        }
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center) {
                            Column(modifier =
                            Modifier.fillMaxWidth().padding(10.dp).border(BorderStroke(2.dp, Color.Black)),
                                horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Background service ❌", fontSize = 20.sp,
                                    modifier = Modifier.padding(10.dp))
                                Text(buildAnnotatedString {
                                    backgroundPoints.forEach {
                                        withStyle(style = paragraphStyle) {
                                            append(bullet)
                                            append("\t\t")
                                            append(it)
                                        }
                                    }
                                }, modifier = Modifier.padding(10.dp))
                                Button(
                                    onClick = {
                                        val serviceIntent = Intent(this@MainActivity, SomeService::class.java)
                                        applicationContext.startService(serviceIntent)
                                }, modifier = Modifier.padding(10.dp)) { Text(text = "Start") }
                            }
                        }
                    }
                }
            }
        }
    }

    companion object {
        private const val bullet = "\u2022"
        private val paragraphStyle = ParagraphStyle(textIndent = TextIndent(restLine = 10.sp))

        private val foregroundPoints = listOf(
            "Tap the button. A service notification will appear.",
            "Swipe kill the app.",
            "Observe that session logs: \"Session stopped\""
        )

        private val backgroundPoints = listOf(
            "Tap the button.",
            "Swipe kill the app.",
            "Observe that the log \"Session stopped\" does not arrive."
        )
    }

}