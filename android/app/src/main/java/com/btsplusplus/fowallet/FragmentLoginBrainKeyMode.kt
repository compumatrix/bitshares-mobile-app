package com.btsplusplus.fowallet

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import bitshares.*
import com.btsplusplus.fowallet.utils.CommonLogic
import com.btsplusplus.fowallet.utils.VcUtils
import com.fowallet.walletcore.bts.WalletManager
import org.json.JSONObject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [FragmentLoginBrainKeyMode.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [FragmentLoginBrainKeyMode.newInstance] factory method to
 * create an instance of this fragment.
 *
 */
class FragmentLoginBrainKeyMode : Fragment() {

    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var _ctx: Context? = null
    private var listener: OnFragmentInteractionListener? = null
    private var _checkActivePermission = true
    private var _result_promise: Promise? = null

    private var _tf_bran_key: EditText? = null
    private var _tf_trade_password: EditText? = null

    lateinit var _iv_unchecked_include_upcase:ImageView
    lateinit var _iv_checked_include_upcase:ImageView
    lateinit var _iv_unchecked_include_downcase:ImageView
    lateinit var _iv_checked_include_downcase:ImageView
    lateinit var _iv_unchecked_include_digit:ImageView
    lateinit var _iv_checked_include_digit:ImageView
    lateinit var _iv_unchecked_length12to40:ImageView
    lateinit var _iv_checked_length12to40:ImageView
    lateinit var _tv_include_upcase: TextView
    lateinit var _tv_include_downcase: TextView
    lateinit var _tv_include_digit: TextView
    lateinit var _tv_length12to40: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    private fun unCheckAllCheckbox(){
        switchIncludeUpcase(false)
        switchIncludeDowncase(false)
        switchIncludeDigit(false)
        switchLength12to40(false)
    }

    private fun switchIncludeUpcase(checked: Boolean){
        if (checked) {
            _iv_unchecked_include_upcase.visibility = View.GONE
            _iv_checked_include_upcase.visibility = View.VISIBLE
            _iv_checked_include_upcase.setColorFilter(resources.getColor(R.color.theme01_textColorMain))
            _tv_include_upcase.setTextColor(resources.getColor(R.color.theme01_textColorMain))
        } else {
            _iv_unchecked_include_upcase.visibility = View.VISIBLE
            _iv_checked_include_upcase.visibility = View.GONE
            _iv_unchecked_include_upcase.setColorFilter(resources.getColor(R.color.theme01_textColorGray))
            _tv_include_upcase.setTextColor(resources.getColor(R.color.theme01_textColorGray))
        }
    }

    private fun switchIncludeDowncase(checked: Boolean){
        if (checked) {
            _iv_unchecked_include_downcase.visibility = View.GONE
            _iv_checked_include_downcase.visibility = View.VISIBLE
            _iv_checked_include_downcase.setColorFilter(resources.getColor(R.color.theme01_textColorMain))
            _tv_include_downcase.setTextColor(resources.getColor(R.color.theme01_textColorMain))
        } else {
            _iv_unchecked_include_downcase.visibility = View.VISIBLE
            _iv_checked_include_downcase.visibility = View.GONE
            _iv_unchecked_include_downcase.setColorFilter(resources.getColor(R.color.theme01_textColorGray))
            _tv_include_downcase.setTextColor(resources.getColor(R.color.theme01_textColorGray))
        }
    }

    private fun switchIncludeDigit(checked: Boolean){
        if (checked) {
            _iv_unchecked_include_digit.visibility = View.GONE
            _iv_checked_include_digit.visibility = View.VISIBLE
            _iv_checked_include_digit.setColorFilter(resources.getColor(R.color.theme01_textColorMain))
            _tv_include_digit.setTextColor(resources.getColor(R.color.theme01_textColorMain))
        } else {
            _iv_unchecked_include_digit.visibility = View.VISIBLE
            _iv_checked_include_digit.visibility = View.GONE
            _iv_unchecked_include_digit.setColorFilter(resources.getColor(R.color.theme01_textColorGray))
            _tv_include_digit.setTextColor(resources.getColor(R.color.theme01_textColorGray))
        }
    }

    private fun switchLength12to40(checked: Boolean){
        if (checked) {
            _iv_unchecked_length12to40.visibility = View.GONE
            _iv_checked_length12to40.visibility = View.VISIBLE
            _iv_checked_length12to40.setColorFilter(resources.getColor(R.color.theme01_textColorMain))
            _tv_length12to40.setTextColor(resources.getColor(R.color.theme01_textColorMain))
        } else {
            _iv_unchecked_length12to40.visibility = View.VISIBLE
            _iv_checked_length12to40.visibility = View.GONE
            _iv_unchecked_length12to40.setColorFilter(resources.getColor(R.color.theme01_textColorGray))
            _tv_length12to40.setTextColor(resources.getColor(R.color.theme01_textColorGray))
        }
    }


    /**
     * 初始化
     */
    fun initWithCheckActivePermission(checkActivePermission: Boolean, result_promise: Promise?): FragmentLoginBrainKeyMode {
        _checkActivePermission = checkActivePermission
        _result_promise = result_promise
        return this
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        _ctx = inflater.context
        val v = inflater.inflate(R.layout.fragment_login_brain_key_mode, container, false)

        // 各种checkbox 和 textview 初始化
        _iv_unchecked_include_upcase = v.findViewById(R.id.iv_unchecked_include_upcase_from_brain_key_mode)
        _iv_checked_include_upcase = v.findViewById(R.id.iv_checked_include_upcase_from_brain_key_mode)
        _iv_unchecked_include_downcase = v.findViewById(R.id.iv_unchecked_include_downcase_from_brain_key_mode)
        _iv_checked_include_downcase = v.findViewById(R.id.iv_checked_include_downcase_from_brain_key_mode)
        _iv_unchecked_include_digit = v.findViewById(R.id.iv_unchecked_include_digit_from_brain_key_mode)
        _iv_checked_include_digit = v.findViewById(R.id.iv_checked_include_digit_from_brain_key_mode)
        _iv_unchecked_length12to40 = v.findViewById(R.id.iv_unchecked_length12to40_from_brain_key_mode)
        _iv_checked_length12to40 = v.findViewById(R.id.iv_checked_length12to40_from_brain_key_mode)
        _tv_include_upcase = v.findViewById(R.id.tv_include_upcase_from_brain_key_mode)
        _tv_include_downcase = v.findViewById(R.id.tv_include_downcase_from_brain_key_mode)
        _tv_include_digit = v.findViewById(R.id.tv_include_digit_from_brain_key_mode)
        _tv_length12to40 = v.findViewById(R.id.tv_length12to40_from_brain_key_mode)

        // 默认所有checkbox不选中
        unCheckAllCheckbox()

        val _button_login: Button = v.findViewById(R.id.button_login_from_brain_key_mode)

        _tf_bran_key = v.findViewById(R.id.tf_brain_key)
        _tf_trade_password = v.findViewById(R.id.tf_trade_password_from_brain_key_mode)

        //  导入到已有钱包：隐藏交易密码。
        if (!_checkActivePermission) {
            v.findViewById<LinearLayout>(R.id.cell_trade_password_from_brain_key_mode).visibility = View.GONE
        }

        _button_login.setOnClickListener {
            onLoginClicked()
        }

        v.findViewById<ImageView>(R.id.tip_link_trading_password_from_brain_key_mode).setOnClickListener {
            VcUtils.gotoQaView(activity!!, "qa_trading_password", resources.getString(R.string.kVcTitleWhatIsTradePassowrd))
        }

        return v
    }

    private fun onLoginClicked() {

        var bran_key = _tf_bran_key!!.text.toString().trim()
        if (bran_key == "") {
            showToast(_ctx!!.resources.getString(R.string.kLoginSubmitTipsBrainKeyIncorrect))
            return
        }
        bran_key = WalletManager.normalizeBrainKey(bran_key)

        //  仅正常登录是才需要验证交易密码，导入到已有钱包不用验证。
        var trade_password = ""
        if (_checkActivePermission) {
            trade_password = _tf_trade_password!!.text.toString()
            if (!Utils.isValidBitsharesWalletPassword(trade_password)) {
                showToast(_ctx!!.resources.getString(R.string.kLoginSubmitTipsTradePasswordFmtIncorrect))
                return
            }
        }

        //  开始登录
        val pub_pri_keys_hash = JSONObject()

        //  根据BIP32、BIP39、BIP44规范，从助记词生成种子、和各种子私钥。
        val hdk = HDWallet.fromMnemonic(bran_key)
        val new_key_owner = hdk.deriveBitshares(EHDBitsharesPermissionType.ehdbpt_owner)
        val new_key_active = hdk.deriveBitshares(EHDBitsharesPermissionType.ehdbpt_active)
        val new_key_memo = hdk.deriveBitshares(EHDBitsharesPermissionType.ehdbpt_memo)

        val pri_key_owner = new_key_owner.toWifPrivateKey()
        val pri_key_active = new_key_active.toWifPrivateKey()
        val pri_key_memo = new_key_memo.toWifPrivateKey()

        val pub_key_owner = OrgUtils.genBtsAddressFromWifPrivateKey(pri_key_owner)
        val pub_key_active = OrgUtils.genBtsAddressFromWifPrivateKey(pri_key_active)
        val pub_key_memo = OrgUtils.genBtsAddressFromWifPrivateKey(pri_key_memo)

        pub_pri_keys_hash.put(pub_key_owner, pri_key_owner)
        pub_pri_keys_hash.put(pub_key_active, pri_key_active)
        pub_pri_keys_hash.put(pub_key_memo, pri_key_memo)

        //  REMARK：兼容轻钱包，根据序列生成私钥匙。
        for (i in 0 until 10) {
            val pri_key = WalletManager.genPrivateKeyFromBrainKey(bran_key, i)
            val pub_key = OrgUtils.genBtsAddressFromWifPrivateKey(pri_key)
            pub_pri_keys_hash.put(pub_key, pri_key)
        }

        //  从各种私钥登录。
        CommonLogic.loginWithKeyHashs(activity!!, pub_pri_keys_hash, _checkActivePermission, trade_password,
                AppCacheManager.EWalletMode.kwmBrainKeyWithWallet.value,
                "login with brainkey",
                _ctx!!.resources.getString(R.string.kLoginSubmitTipsBrainKeyIncorrect),
                _ctx!!.resources.getString(R.string.kLoginSubmitTipsPermissionNotEnoughAndCannotBeImported),
                _result_promise)
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments]
     * (http://developer.android.com/training/basics/fragments/communicating.html)
     * for more information.
     */
    interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentLoginBrainKeyMode.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
                FragmentLoginBrainKeyMode().apply {
                    arguments = Bundle().apply {
                        putString(ARG_PARAM1, param1)
                        putString(ARG_PARAM2, param2)
                    }
                }
    }
}
