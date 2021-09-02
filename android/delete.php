<?php

    if ($_SERVER['REQUEST_METHOD'] == 'POST') {
        require_once("db.php");

        $id = $_POST['id'];

        $query = "DELETE FROM users WHERE id = '$id'";
        $result = $mysql->query($query);

        if ($mysql->affected_rows > 0) {
            if ($result === TRUE) {echo "The user was removed successfully.";}
        }
        else {echo "Not found any rows";}

        $mysql->close();
    }

?>