
const express = require('express')
const app = express()

//const session = require('express-session')

const mysql = require('mysql')

//app.use(express.static("public"));
//app.use(express.json());

const pool  = mysql.createPool({
    connectionLimit : 10,
    host            : 'localhost',
    user            : 'root',
    password        : 'password',
    database        : 'nodejs_beers'
})

app.post("/signUp", (req, res) => {
    //console.log(req)

    const { username, password, email } = req.query;

	console.log("SignUp Request: "+username+" "+password+" "+email)
    
	pool.getConnection((err, connection) => {
        if(err) throw err
        console.log('connected as id ' + connection.threadId)
        connection.query('INSERT INTO CREDENTIALS VALUES('+username+','+password+','+email+')' , (err, rows) => {
            connection.release() // return the connection to pool
            if (!err) {
                res.send('Signed Up')
            } else {
                console.log(err)
                res.send('Error')
            }
            // if(err) throw err
        })
    })
})

app.post("/logIn", (req, res) => {
    //console.log(req)

    const { email, password } = req.query;

	console.log("Log In Request: "+email+" "+password)

    pool.getConnection((err, connection) => {
        if(err) throw err
        console.log('connected as id ' + connection.threadId)
        connection.query('SELECT * from credentials WHERE username = '+username+' , password = '+password, (err, rows) => {
            connection.release() // return the connection to pool

            if (!err) {
                if(rows.length == 0){
                    res.send('User doesn\'t exist')
                }
                res.send(rows[0])
            } else {
                console.log(err)
            }

            // if(err) throw err
            console.log('The data from credentials table are: \n', rows)
        })
    })
})

app.listen(3001,() => {
    console.log('\nListening to localhost:3001');
});

