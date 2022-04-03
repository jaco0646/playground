package stackoverflow.questions.chords

import spock.lang.Ignore
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class ChordsSpec extends Specification {

    def testLineChords() {
        expect:
            Chords.formatLineChords(input1) == output1
        where:
            input1                                                                              | output1
            'Well I [E] tried to make it sunday but I [G#min] got so damned depressed'          | '       E                              G#min'
            'That I [A] set my sights on [E] monday and I [G#min] got myself undressed'         | '       A                 E             G#min'
            'I ain\'t ready for the alter, but I [C#min] do [G#min] believe there\'s [A] times' | '                                   C#min G#min          A'
            'When a [F#min] woman sure can [A] be a friend of [E] mine [Esus2] [E]'             | '       F#min           A               E     Esus2 E'
    }

    def testLineVerses() {
        expect:
            Chords.formatLineVerse(input2) == output2
        where:
            input2                                                                              | output2
            'Well I [E] tried to make it sunday but I [G#min] got so damned depressed'          | 'Well I  tried to make it sunday but I  got so damned depressed'
            'That I [A] set my sights on [E] monday and I [G#min] got myself undressed'         | 'That I  set my sights on  monday and I  got myself undressed'
            'I ain\'t ready for the alter, but I [C#min] do [G#min] believe there\'s [A] times' | 'I ain\'t ready for the alter, but I  do  believe there\'s  times'
            'When a [F#min] woman sure can [A] be a friend of [E] mine [Esus2] [E]'             | 'When a  woman sure can  be a friend of  mine'
    }

    def testSong() {
        expect:
            Chords.formatChords(song).length() == expectedSong.length()
    }

    @Ignore
    def test() {
        expect:
            println "\n\n"
            println Chords.formatChords(song)
            println "\n\n"
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    def song = '''\
        Well I [E] tried to make it sunday but I [G#min] got so damned depressed
        That I [A] set my sights on [E] monday and I [G#min] got myself undressed
        I ain't ready for the alter, but I [C#min] do [G#min] believe there's [A] times
        When a [F#min] woman sure can [A] be a friend of [E] mine [Esus2] [E]'''.stripIndent()

    def expectedSong = '''\
               E                              G#min
        Well I  tried to make it sunday but I  got so damned depressed
               A                 E             G#min
        That I  set my sights on  monday and I  got myself undressed
                                           C#min G#min          A
        I ain't ready for the alter, but I  do  believe there's  times
               F#min           A               E     Esus2 E
        When a  woman sure can  be a friend of  mine'''.stripIndent()
}
