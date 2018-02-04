package fr.acinq.eclair

import akka.actor.ActorSystem
import fr.acinq.bitcoin.{Block, Transaction}
import fr.acinq.eclair.blockchain._
import fr.acinq.eclair.blockchain.bitcoind.rpc.{BitcoinJsonRPCClient, ExtendedBitcoinClient}

import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

/**
  * Created by PM on 26/04/2016.
  */
class TestBitcoinClient()(implicit system: ActorSystem) extends ExtendedBitcoinClient(new BitcoinJsonRPCClient("", "", "", 0)) {

  import scala.concurrent.ExecutionContext.Implicits.global

  val DUMMY_BLOCK = Block.read("00000020205bba6a20bd1cff58417887cdc7398ba84c98c12aa6fccdece1dd4768428435bd0cda8624fb7dd0a7cd0a4aa118d760c75b907748cea12545b151f4bcc9b9293542de58ffff7f20000000000a020000000001010000000000000000000000000000000000000000000000000000000000000000ffffffff0502f5010101ffffffff02944b4125000000002321032f7df99233015bfd868d1bd6602193c32db32e696bda25c9652b88bce6413736ac0000000000000000266a24aa21a9ed589d96bf3da1c64da770959b24f8740d7be6269c0a8a4ed4a3163e342825a55701200000000000000000000000000000000000000000000000000000000000000000000000000200000001d1a6674947980f08459148b7aef5ba3e1922d86db35e855d592b6b695ddd953a00000000484730440220292751a637cb1622b3cbea2d699e2973857cd2602db6c4cfce591d0348d2bfcd02207030ca945e215c0a86ea2c8def133bb364e3ef467b091405e7b2e940d27a7ca101feffffff0220a10700000000002200209a7db6df4a917cb04f1e156f3f61403127e62a27179da88c6e6f95eb36788649accb794a000000001976a914006671f74db779465ee1bc7dacaef94b99ad637588ac00000000020000000139cbc7fa601dc05c9868db96200fbd4420f00e366e74a59ce9508ec61478283d0000000048473044022060d37121ef0994c98702727caafbfdd8140ded82fc17dd76fd674bd8287e49ec0220429f64cd2378581520082e02956d56e8d391b3f6928c2722e6c59a6038a941cf01feffffff0220a10700000000002200208cd4259c811ad83d8fd09641e28d7857de688a381d60f6b8585ac972a8761933accb794a000000001976a914442e4fc451c3f3c1679eb82d73e804fa11b1476b88ac0000000002000000013a5b52f7d2296a0e3e939e4f1aeec9db58d8a4e21cedadfa58c805b68d7153be0000000048473044022046f0521ba8968e72da92d8e4bcb3242b6bcfae85693281e5b2b79310d56fdbad02205a0fc0d5aa96adef1072ce3bf4f985104d81d37dce76a4746b3ede4be12cadd901feffffff028c2a724a000000001976a91469ebcc84280416b43bde82eba104e97f632a5a8788ac40420f000000000022002007a37835263ca056aa4155538e0e7c3deb6525a29cb645ba3b0790eaba2540100000000002000000010f040b9e487f113dd11e24f19bd2a7c416cd7887e99d8f9fce70cf287dd6a37e0000000048473044022005b6a9448d5a59c6a23d80022b779c17de6960b4371a0ceed0d54263adaeec1e0220541b88d0c1fa95ef331d819546eae7da676a24222478986d85a964b4afde8f8b01feffffff0220a1070000000000220020b90c33cddf048d678f52529be79ac6f565c353832857485067f1e27807347ba6accb794a000000001976a914f4d69731dc7bd9b01c72839e4d6f2571c810e9dd88ac000000000200000001c981b5585cfe6b7af535d9371960521c91b6ca38f985e7821aca30e3768927f1000000004948304502210086851b6f130df49ffc0d67e4ecf7d3c70ceaaa6e9e0218a3f6f3c070921eadc90220057788d0e5b01cefbe13e40ac05e97a766cec9ca8283955a3e2ed4f02c203df301feffffff02accb794a000000001976a914d5a86a097eacef68d0929d32330d091b71200e2e88ac20a10700000000002200202457e4d21696673e19cbe3c5e668315209bf8f8c7d60bd97a279c56553c90de90000000002000000019c81715e6a87bbd060abef210df0b27b98c7393a2200f2a3754049cee44f00640000000049483045022100ace646f4a55147d6a24cefbb98761019a02623a1959ec3d406d7d0b58b13e01302204fb83cf990e1f00cf4fad1ff632f0e83aa727effd70c61a6f9d80a5b4f9fb05901feffffff0220a1070000000000220020fdc4862d7e2e0fe778d030c4a482e9ac05779acfa0e8fcd86a13ebdb877e1619accb794a000000001976a914a42ea72affe4361fbec2dbd7464ad8b3146e608788ac000000000200000001379b12d406d5182899dd89727a819132908e3498ba7768fe32911416045b6a910000000049483045022100f3f58d60aafdee5778055708cde67b5264fcf209f3b6ee42247dc640530a4888022057dbadbebb1b272ea66e3f75c1cf5bcfc956922c8d21beb0154740d6e2a3e5c601feffffff0220a10700000000002200207362c1cc15eedd2f2acf1ff454d2829e2500b5e71c4cb1e84a2c1628ad88ef9eaccb794a000000001976a914118c61ac92f9368ce07e88d4d32032ab56f5536888ac0000000002000000011860e03265da09943e2520075a352b892c28c440822cacb5c034bc5ac77483280000000049483045022100a74676977d60b369c1e7b42ecd676f3331ae67effd3ccb23aa6ea4e1575b2dc002204a8ff577eaa9a70f9a7896e6ef2748108e0cbdc4a27155f35693619feac8061c01feffffff02400d0300000000002200209519671c042047476f9ef22c058fd047f16fc3e97ec2edde04b925248abc39fc8c5f7e4a000000001976a91448c4340fe6b9f1e3175b4dde7f17902440d1310f88ac000000000200000001cd22950932eead006a1ac0142c65c00b11ff669fd0eb632130a5a7b14913954e0000000049483045022100fa7e02b36cd76ba33cfe9fefddc7c6d6eca256db03c47da6938049ab367794e502206f2123a4b9535b1f61117ab7b3147acd519607487d61a76391dc662571bcc71301feffffff0220a1070000000000220020b089934f5593c95e26bb4f08d5ef306796c8cb5818d9fe266d2c9d36698eeeaaaccb794a000000001976a9148aea65fe65387160cf60cdfed44345963d24506088ac00000000")

  system.scheduler.schedule(100 milliseconds, 100 milliseconds, new Runnable {
    override def run(): Unit = system.eventStream.publish(NewBlock(DUMMY_BLOCK)) // blocks are not actually interpreted
  })

  override def publishTransaction(tx: Transaction)(implicit ec: ExecutionContext): Future[String] = {
    system.eventStream.publish(NewTransaction(tx))
    Future.successful(tx.txid.toString())
  }

  override def getTxConfirmations(txId: String)(implicit ec: ExecutionContext): Future[Option[Int]] = Future.successful(Some(10))

  override def getTransaction(txId: String)(implicit ec: ExecutionContext): Future[Transaction] = ???

  override def getTransactionShortId(txId: String)(implicit ec: ExecutionContext): Future[(Int, Int)] = Future.successful((400000, 42))

}