const mysql = require("mysql2");

const select = () => {
  const connection = (mysql.connection = mysql.createConnection({
    host: "192.168.0.122",
    user: "root",
    password: "1234",
    database: "nodejs",
  }));

  connection.connect(() => {
    console.log("db연결성공");
  });
  connection.query("select * from users", (err, result) => {
    console.log(result);
    connection.end();
  });

  console.log("db연결종료");
};
module.exports = { select };
