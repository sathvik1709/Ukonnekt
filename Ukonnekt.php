<?php

	if(function_exists($_GET['method']))
	{
		$_GET['method']();
	}
	
	function connectToDatabase()
	{
		$returnValue = true;
		try
		{
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
			/* check connection */
			if (mysqli_connect_errno())
			{
				printf("Connect failed: %s\n", mysqli_connect_error());
				exit();
			}
			
			if (!$link)
			{
				$returnValue = false;
			}
		}
		catch(Exception $e)
		{
			$returnValue = false;		
		}

		return $returnValue;
	}
	
	function closeDBConnection()
	{
		echo "Disconnecting";
		if(mysqli_close($link))
		{
			echo "Disconnected";
		}
	}
	
	
	function signInUser()
	{
		$returnValue = array();
		
		if(isset($_POST["username"]) && isset($_POST["password"]) &&
			null != $_POST["username"] && null != $_POST["password"])
		{
			$username = $_POST["username"];
			$password = $_POST["password"];
						
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
		
			if (!mysqli_connect_errno())
			{
				$query = "SELECT UnivId, FirstName, LastName FROM student WHERE NetId = '$username' AND Password = '$password'";
				
				$queryResult = mysqli_query($link, $query);
				$row = mysqli_fetch_array($queryResult, MYSQLI_ASSOC);
				
				if(null != $row)
				{				
						$returnValue[] = array('Error Code' => "0",
										  'University_ID' => $row['UnivId'],
										  'First_name' => $row['FirstName'],
										  'Last_name' => $row['LastName']);
				}
				else
				{
					$returnValue[] = array('Error Code' => "1");
				}
					
				mysqli_free_result($queryResult);
			}
		
			mysqli_close($link);
			
			$returnValue = json_encode($returnValue);			
		}
		
		echo $_GET['signinusercallback']. ''. $returnValue .'';
	}

	function postAnnouncement()
	{
		//$returnValue = array();
		
		if(isset($_POST["title"]) && isset($_POST["description"]) &&
			null != $_POST["title"] && null != $_POST["description"])
		{
            //$timedate = $_POST["timedate"];
			$title = $_POST["title"];
			$description = $_POST["description"];
						
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
		
			if (!mysqli_connect_errno())
			{
				$query = "INSERT into announcements (AnnTopic, AnnContent) VALUES ('$title', '$description')";
				
				$queryResult = mysqli_query($link, $query);
			//	$row = mysqli_fetch_array($queryResult, MYSQLI_ASSOC);
				
			/*	if(null != $row)
				{				
						$returnValue[] = array('Error Code' => "0",
										  'University_ID' => $row['UnivId'],
										  'First_name' => $row['FirstName'],
										  'Last_name' => $row['LastName']);
				}
				else
				{
					$returnValue[] = array('Error Code' => "1");
				}
            */
				mysqli_free_result($queryResult);
			}
		
			mysqli_close($link);
			
			//$returnValue = json_encode($returnValue);			
		}
		
		//echo $_GET['postAnnouncement'];
	}
    function getTimetable()
	{
		$returnValue = array();
		
		if(isset($_POST["today_date"]) && isset($_POST["results_For"]) &&
			null != $_POST["today_date"] && null != $_POST["results_For"])
		{
			$today_date = $_POST["today_date"];	
            $results_For = $_POST["results_For"];
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
            
		
			if (!mysqli_connect_errno())
			{
               
				if(strcmp($results_For,"Upcoming") == 0){
                $Date = date('Y-m-d', strtotime($today_date. ' + 3 days'));
				$query = "SELECT * FROM syllabusmeter WHERE Date > '$today_date' And Date < '$Date' ORDER BY Date";
                }
                else if(strcmp($results_For,"today") == 0){
                $query = "SELECT * FROM syllabusmeter WHERE Date = '$today_date' ORDER BY start_time";
                }
                    
				$queryResult = mysqli_query($link, $query);				
				if($queryResult = mysqli_query($link, $query))
				{				
                    while($row = mysqli_fetch_array($queryResult, MYSQLI_ASSOC)){
						$returnValue[] = array('Error Code' => "0",
										  'courseID' => $row['CourseId'],
										  'courseTopic' => $row['CourseTopics'],
										  'status' => $row['Status'],
                                          'date' => $row['Date'],
										  'start_time' => $row['start_time'],
										  'end_time' => $row['end_time']);
                    }
                    mysqli_free_result($queryResult);
				}
				else
				{
					$returnValue[] = array('Error Code' => "1");
				}
					
				
			}
		
			mysqli_close($link);
			
			$returnValue = json_encode($returnValue);			
		}
		
		echo $_GET['getTimetablecallback']. ''. $returnValue .'';
	}

    function getAnnouncement()
	{
		$returnValue = array();
		
		if(isset($_POST["courseId"]) && isset($_POST["annOrAssign"]) &&
			null != $_POST["courseId"] && null != $_POST["annOrAssign"])
		{
			$course_Id = $_POST["courseId"];	
            $ann_Or_Assign = $_POST["annOrAssign"];
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
            
		
			if (!mysqli_connect_errno())
			{
               
				if(strcmp($ann_Or_Assign,"Announcement") == 0){
                $query = "SELECT * FROM announcements WHERE course_id = '$course_Id' And ann_topic = 'Announcement' ORDER BY due_date";
                }
                else if(strcmp($ann_Or_Assign,"Assignment") == 0){
                $query = "SELECT * FROM announcements WHERE course_id = '$course_Id' And ann_topic != 'Announcement' ORDER BY due_date";
                }
                    
				$queryResult = mysqli_query($link, $query);				
				if($queryResult = mysqli_query($link, $query))
				{				
                    while($row = mysqli_fetch_array($queryResult, MYSQLI_ASSOC)){
						$returnValue[] = array('Error Code' => "0",
										  'title' => $row['ann_topic'],
										  'dueDate' => $row['due_date'],
										  'description' => $row['ann_content']);
                    }
                    mysqli_free_result($queryResult);
				}
				else
				{
					$returnValue[] = array('Error Code' => "1");
				}
					
				
			}
		
			mysqli_close($link);
			
			$returnValue = json_encode($returnValue);			
		}
		
		echo $_GET['getAnnouncementcallback']. ''. $returnValue .'';
	}

    function getGrade()
	{
		$returnValue = array();
		
		if(isset($_POST["studentId"]) && isset($_POST["courseId"]) &&
			null != $_POST["studentId"] && null != $_POST["courseId"])
		{
			$student_Id = $_POST["studentId"];	
            $course_Id = $_POST["courseId"];
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
            
		
			if (!mysqli_connect_errno())
			{
               
				$query = "SELECT * FROM grade WHERE StudentId = '$student_Id' And CourseId = '$course_Id' ";
                    
				$queryResult = mysqli_query($link, $query);				
				if($queryResult = mysqli_query($link, $query))
				{				
                    while($row = mysqli_fetch_array($queryResult, MYSQLI_ASSOC)){
						$returnValue[] = array('Error Code' => "0",
										  'examId' => $row['ExamId'],
										  'grade' => $row['grade'],
										  'maxGrade' => $row['MaxGrade']);
                    }
                    mysqli_free_result($queryResult);
				}
				else
				{
					$returnValue[] = array('Error Code' => "1");
				}
					
				
			}
		
			mysqli_close($link);
			
			$returnValue = json_encode($returnValue);			
		}
		
		echo $_GET['getGradecallback']. ''. $returnValue .'';
	}

function getSyllabusMeter()
	{
		$returnValue = array();
		
		if(isset($_POST["courseID"])  &&
			null != $_POST["courseID"] )
		{
			$courseID = $_POST["courseID"];	
			$link = mysqli_connect("omega.uta.edu", "sxs9203", "md79fY75", "sxs9203");
            
		
			if (!mysqli_connect_errno())
			{
                $query = "SELECT * FROM syllabusmeter WHERE CourseId = '$courseID'";  
				$queryResult = mysqli_query($link, $query);		
                $totalClasses = $queryResult->num_rows;
                
                $query1 = "SELECT * FROM syllabusmeter WHERE CourseId = '$courseID' AND Status = 1";  
				$queryResult1 = mysqli_query($link, $query1);		
                $completedClasses = $queryResult1->num_rows;
                            
                $returnValue[] = array('Error Code' => "0",
										  'totalClasses' => $totalClasses,
										  'completedClasses' => $completedClasses);		
				
			}
		
			mysqli_close($link);
			
			$returnValue = json_encode($returnValue);			
		}
		
		echo $_GET['getSyllabusMetercallback']. ''. $returnValue .'';
	}

	function test()
	{
		echo true;
	}
	
?>