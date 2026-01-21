package com.example.proyectotest
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.Image
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource


/*class SearchByName : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {


            ProyectoTestTheme {

                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {


                    val viewModel = NurseViewModel()


                    SearchView(viewModel)
                }
            }
        }
    }
}
*/

@Composable
fun SearchView(
    viewModel: NurseViewModel,
    onNavigateBack: () -> Unit
){
    val context = LocalContext.current
    var textSearch by remember { mutableStateOf("") }
    val nurses by viewModel.nurseList.observeAsState(initial = emptyList())

    Scaffold(
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    onNavigateBack()
                },
                modifier = Modifier.padding(start = 16.dp),
                containerColor = MaterialTheme.colorScheme.primary
            ) {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = stringResource(R.string.homepage)
                )
            }
        },
        floatingActionButtonPosition = FabPosition.Start
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {

            TextField(
                value = textSearch,
                onValueChange = { textSearch = it
                    viewModel.fetchNursesByName(it)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(60.dp),
                placeholder = {
                    Text(text = stringResource(R.string.name_to_search))
                },
                maxLines = 1,
                singleLine = true,
                textStyle = TextStyle(
                    color = colorResource(id = R.color.black),
                    fontSize = 20.sp
                ),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.Search,
                        contentDescription = null
                    )
                },
                shape = RoundedCornerShape(8.dp),
                colors = TextFieldDefaults.colors(
                    focusedIndicatorColor = colorResource(id = R.color.transparent),
                    unfocusedIndicatorColor = colorResource(id = R.color.transparent)
                )
            )

            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(nurses) { nurse ->

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Image(
                            painter = painterResource(id = R.drawable.nurse_generico), // Usa uno que sepas que existe
                            contentDescription = null,
                            modifier = Modifier.size(50.dp).clip(CircleShape)
                        )

                        Spacer(modifier = Modifier.width(16.dp))


                        Text(
                            text = nurse.name,
                            style = MaterialTheme.typography.titleMedium
                        )
                    }

                    Divider(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .padding(vertical = 4.dp)
                    )


                }
            }
        }
    }

}