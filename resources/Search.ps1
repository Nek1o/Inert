#
# Generates a full list of installed programs.
#

# Temporary auxiliar file.
$tmpFile = "tmp.txt"

# File that will hold the programs list.
$fileName = "programas-instalados.txt"

# Columns separator.
$separator = ","

# Delete previous files.

Remove-Item $tmpFile
Remove-Item $fileName

# Creates the temporary file.

Create-Item $tmpFile

# Searchs register for programs - part 1
$loc = Get-ChildItem HKLM:\Software\Microsoft\Windows\CurrentVersion\Uninstall
$names = $loc |foreach-object {Get-ItemProperty $_.PsPath}
foreach ($name in $names)
{
    IF(-Not [string]::IsNullOrEmpty($name.DisplayName)) {      
        $line = $name.DisplayName
        Write-Host $line
        Add-Content $tmpFile "$line`n"        
    }
}

# Searchs register for programs - part 2
$loc = Get-ChildItem HKLM:\Software\Wow6432Node\Microsoft\Windows\CurrentVersion\Uninstall
$names = $loc |foreach-object {Get-ItemProperty $_.PsPath}
foreach ($name in $names)
{
    IF(-Not [string]::IsNullOrEmpty($name.DisplayName)) {      
        $line = $name.DisplayName
        Write-Host $line
        Add-Content $tmpFile "$line`n"
    }
}

# Sorts the result, removes duplicate lines and
# generates the final file.
gc $tmpFile | sort | get-unique > $filename