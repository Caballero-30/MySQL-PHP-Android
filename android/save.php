<?php

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        require_once("db.php");

        $name = $_POST['name'];
        $email = $_POST['email'];
        $password = $_POST['password'];
        $phone = $_POST['phone'];

        $query = "INSERT INTO users (name, email, password, phone) VALUES ('$name', '$email', '$password', '$phone')";
        $result = $mysql->query($query);

        if ($result === TRUE) {echo "The user was created successfully.";}
        else {echo "Error";}
        
        $mysql->close();
    }

?>