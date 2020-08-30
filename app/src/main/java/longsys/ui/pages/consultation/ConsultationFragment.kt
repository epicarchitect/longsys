package longsys.ui.pages.consultation

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.LinkMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.consultation_fragment.*
import longsys.R

class ConsultationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) =
        inflater.inflate(R.layout.consultation_fragment, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        tvAboutDoctor.apply {
            text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                Html.fromHtml(ABOUT_DOCTOR, Html.FROM_HTML_MODE_LEGACY)
            } else {
                Html.fromHtml(ABOUT_DOCTOR)
            }
            movementMethod = LinkMovementMethod.getInstance()
        }

        buttonEmail.setOnClickListener { goToEmail() }
        buttonVk.setOnClickListener { goToVk() }
        buttonWhatsApp.setOnClickListener { goToWhatsApp() }
        buttonTelegram.setOnClickListener { goToTelegram() }
        buttonViber.setOnClickListener { goToViber() }
    }


    fun goToEmail() {
        activity?.startActivity(
            Intent.createChooser(
                Intent(Intent.ACTION_SENDTO, Uri.parse("mailto:$EMAIL")),
                "Выбирите приложение"
            )
        )
    }

    fun goToVk() {
        activity?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://vk.com/$VK")
            )
        )
    }

    fun goToWhatsApp() {
        activity?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://api.whatsapp.com/send?phone=$WHATSAPP")
            )
        )
    }

    fun goToTelegram() {
        activity?.startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("http://telegram.me/$TELEGRAM")
            )
        )
    }

    fun goToViber() {
        try {
            activity?.startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("viber://add?number=$VIBER")
                )
            )
        } catch (e: Exception) {
            Toast.makeText(context, "Viber не найден", Toast.LENGTH_SHORT).show()
        }

    }

    companion object {
        const val EMAIL = "amenion_youtube@mail.ru"
        const val VK = "id14062422"
        const val TELEGRAM = "amenion_youtube"
        const val WHATSAPP = "+4917634868634"
        const val VIBER = "4917634868634"

        const val ABOUT_DOCTOR = "Непосредственный участник в разработке препаратов <a href='https://compendium.com.ua/info/132866/al_tan/'>Альтан</a> и <a href='http://www.vestnik.vsu.ru/pdf/chembio/2003/02/levitin_oridoroga.pdf'>Анальбен</a>." +
                "<br><br>Автор теории влияния перекисного окисления на процессы старения и разработчик оригинального метода получения <a href='http://www.provisor.com.ua/archive/1998/N22/el_acid.php?part_code=8&art_code=1338'>Эллаговой кислоты</a>, патент Украины № 23109А, 6А61К 35/78." +
                "<br>Создатель и ведущий Ютуб-канала <a href='https://www.youtube.com/channel/UCIsVz_0KSzG4ynmVe3dVRkQ'>Аменион</a>." +
                "<br>Автор теории <a href='https://youtu.be/W0frHph0xg4'>Стероидного минимализма 3.0</a>" +
                "<br><br>В данный момент работает в Германии, в руководстве концерна <a href='https://www.helios-gesundheit.de/kliniken/wuppertal/'>Helios</a>, специализация урология и гастроэнтерология."
    }


}