<?php

    $mysql = new mysqli("localhost", "root", "root", "android", "3306");

    if ($mysql->connect_error) {die("Failed to connect" . $mysql->connect_error);}
    // else {echo "Successfully";}

?>