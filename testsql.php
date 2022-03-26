<?php

$des = new CryptDes();

$mres = $des->decrypt(file_get_contents("php://input"));

$mysql_server="localhost"; // 数据库地址，默认为3306

$mysql_username="root"; // 数据库用户名

$mysql_userpass="123456"; // 数据库密码

$mysql_select_db="security"; // 数据库库名

$evil_array =  json_decode($mres,TRUE);

$config=mysqli_connect($mysql_server,$mysql_username,$mysql_userpass,$mysql_select_db);

$query  = "select * from users where id = '".$evil_array['id']."'";


$result = mysqli_query( $config, $query) or die(mysqli_error($config));

$data = mysqli_fetch_all($result); // 从结果集中获取所有数据

foreach($data as $v){
    $values = array_values($v);
    $ids = array_keys($v);
    


$mres3 = $des->encrypt($values[1]); //加密
echo $mres3;
}

mysqli_close($config);


mysql_error();
$mres = $des->encrypt($query); //加密

class CryptDes {
    function __construct(){
     $this->key = 'f0ngtest'; //密钥
     $this->iv = 'f0ngf0ng'; //偏移量
    }
    /*
     * 加密
     */
    function encrypt($input){
     $size = mcrypt_get_block_size(MCRYPT_DES,MCRYPT_MODE_CBC); //3DES加密将MCRYPT_DES改为MCRYPT_3DES
     $input = $this->pkcs5_pad($input, $size); //如果采用PaddingPKCS7，请更换成PaddingPKCS7方法。
     $key = str_pad($this->key,8,'0'); //3DES加密将8改为24
     $td = mcrypt_module_open(MCRYPT_DES, '', MCRYPT_MODE_CBC, '');
     if( $this->iv == '' )
     {
      $iv = @mcrypt_create_iv (mcrypt_enc_get_iv_size($td), MCRYPT_RAND);
     }
     else
     {
      $iv = $this->iv;
     }
     @mcrypt_generic_init($td, $key, $iv);
     $data = mcrypt_generic($td, $input);
     mcrypt_generic_deinit($td);
     mcrypt_module_close($td);
     $data = base64_encode($data);//如需转换二进制可改成 bin2hex 转换
     return $data;
    }
    /*
     * 解密
     */
    function decrypt($encrypted){
     $encrypted = base64_decode($encrypted); //如需转换二进制可改成 bin2hex 转换
     $key = str_pad($this->key,8,'0'); //3DES加密将8改为24
     $td = mcrypt_module_open(MCRYPT_DES,'',MCRYPT_MODE_CBC,'');//3DES加密将MCRYPT_DES改为MCRYPT_3DES
     if( $this->iv == '' )
     {
      $iv = @mcrypt_create_iv (mcrypt_enc_get_iv_size($td), MCRYPT_RAND);
     }
     else
     {
      $iv = $this->iv;
     }
     $ks = mcrypt_enc_get_key_size($td);
     @mcrypt_generic_init($td, $key, $iv);
     $decrypted = mdecrypt_generic($td, $encrypted);
     mcrypt_generic_deinit($td);
     mcrypt_module_close($td);
     $y=$this->pkcs5_unpad($decrypted);
     return $y;
    }
    function pkcs5_pad ($text, $blocksize) {
     $pad = $blocksize - (strlen($text) % $blocksize);
     return $text . str_repeat(chr($pad), $pad);
    }
    function pkcs5_unpad($text){
     $pad = ord($text{strlen($text)-1});
     if ($pad > strlen($text)) {
      return false;
     }
     if (strspn($text, chr($pad), strlen($text) - $pad) != $pad){
      return false;
     }
     return substr($text, 0, -1 * $pad);
    }
    function PaddingPKCS7($data) {
     $block_size = mcrypt_get_block_size(MCRYPT_DES, MCRYPT_MODE_CBC);//3DES加密将MCRYPT_DES改为MCRYPT_3DES
     $padding_char = $block_size - (strlen($data) % $block_size);
     $data .= str_repeat(chr($padding_char),$padding_char);
     return $data;
    }
   }
   