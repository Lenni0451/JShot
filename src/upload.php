<?php

	if(!isset($_GET['security-token'])) {
		die("There is no security token set. Please try again with one.");
	}
	$securityToken = $_GET['security-token'];
	if($securityToken != '<TOKEN>') {
		die("You've provided an invalid security token. Please check it and try again.");
	}

	$fileData = file_get_contents('php://input');
	if(strlen($fileData) <= 0) {
		die("The given image data is empty.");
	}
	$fileName = md5($fileData) . '.png';
	
	$file = fopen($fileName, 'wb');
	fwrite($file, $fileData);
	fclose($file);
	echo($fileName);

?>